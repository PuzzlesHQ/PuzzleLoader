package com.github.puzzle.core.loader.engine;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.engine.events.GameLoaderFinishEvent;
import com.github.puzzle.core.loader.engine.events.RegisterStagesEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameLoader {

    public static final ArrayList<GameLoader> INSTANCES = new ArrayList<>();
    public final AtomicBoolean finished = new AtomicBoolean();

    private final AtomicBoolean shouldClose = new AtomicBoolean();

    private final Queue<Stage> stages = new LinkedList<>();
    private final Queue<Runnable> glQueue = new LinkedList<>();

    public ProgressBar bar1;
    public ProgressBar bar2;
    public ProgressBar bar3;

    private Thread thread;

    public GameLoader() {
        INSTANCES.add(this);
    }

    public void create() {
        bar1.setVisible(false);
        bar2.setVisible(false);
        bar3.setVisible(false);

        Constants.EVENT_BUS.post(new RegisterStagesEvent(this));

        thread = new Thread(this::gameLoadingThread, "Game-Loader");
//        loading.setUncaughtExceptionHandler();
        thread.setDaemon(true);
        thread.start();
    }

    private final List<Stage> coreStages = new ArrayList<>();
    private final List<Stage> otherStages = new ArrayList<>();

    public void register(Stage stage) {
        if (
                stage.getClass().getPackageName().startsWith("com.github.puzzle") ||
                        stage.getClass().getPackageName().startsWith("io.github.puzzle")
        ) {
            coreStages.add(stage);
            return;
        }

        otherStages.add(stage);
    }

    public void update() {
        long endTime = System.currentTimeMillis() + 50;
        while (!glQueue.isEmpty() && System.currentTimeMillis() < endTime) {
            Runnable glTask = glQueue.poll();
            if(glTask != null) {
                glTask.run();
            }
        }
    }

    public void gameLoadingThread() {
        stages.addAll(coreStages);
        stages.addAll(otherStages);
        coreStages.clear();
        otherStages.clear();

        bar1.setVisible(true);
        bar1.setMax(stages.size());
        int stagesCount = stages.size();
        int count = 0;
        while (!stages.isEmpty()) {
            if (Constants.shouldClose() || shouldClose.get()) {
                System.out.println("Cleaning up && Shutting down game-loader");
                cleanup();
                return;
            }

            CountDownLatch lock = new CountDownLatch(1);
            Stage stage = stages.poll();

            bar1.setProgress(++count);
            bar1.setText(String.format("Stage §[03bfd4]%d§[008a99]/§[03bfd4]%d§r: %s", count, stagesCount, stage.getName()));

            assert stage != null;
            stage.setGameLoader(this);
            stage.doStage();
            glQueue.addAll(stage.glTasks());
            glQueue.add(lock::countDown);

            try {
                lock.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.gc();
        }

        Constants.EVENT_BUS.post(new GameLoaderFinishEvent());

        this.cleanup();

        System.out.println("Finished Game Loading");
        finished.set(true);
    }

    private void cleanup() {
        bar1 = null;
        bar2 = null;
        bar3 = null;

        stages.clear();
        coreStages.clear();
        otherStages.clear();
    }

    public void close() {
        shouldClose.set(true);
    }

    public static void killAll() {
        for (GameLoader loader : INSTANCES) {
            loader.close();
        }
    }

    public interface Stage {

        void setGameLoader(GameLoader loader);
        GameLoader getGameLoader();

        void doStage();

        List<Runnable> glTasks();

        String getName();
    }

    public interface ProgressBar {

        ProgressBar NULL_BAR = new ProgressBar() {
            @Override
            public void setText(String s) {}

            @Override
            public void setProgress(float progress) {}

            @Override
            public void setMax(int max) {}

            @Override
            public void setVisible(boolean b) {}
        };

        void setText(String s);
        void setProgress(float progress);
        void setMax(int max);

        void setVisible(boolean b);
    }

}
