package io.github.puzzle.cosmic.api.item.container;

import com.badlogic.gdx.math.Vector3;
import io.github.puzzle.cosmic.api.item.IPuzzleItemSlot;
import io.github.puzzle.cosmic.api.item.IPuzzleItemStack;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.Note;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */

@ApiGen("SlotContainer")
@Note("This interface only applies to SlotContainer and not ISlotContainer internally.")
public interface IPuzzleSlotContainer {

    IPuzzleSlotContainer _getBackingContainer();

    boolean _addItemStack(IPuzzleItemStack stack);
    boolean _addItemStackWithSwapGroup(IPuzzleItemStack stack);

    boolean _addOrMergeFrom(IPuzzleItemSlot slot);
    boolean _addOrMergeFrom(IPuzzleItemSlot slot, Predicate<IPuzzleItemSlot> slotPredicate);

    void _dropAllItems(IPuzzleZone zone, float x, float y, float z);
    default void _dropAllItems(IPuzzleZone zone, Vector3 position) {
        _dropAllItems(zone, position.x, position.y, position.z);
    }

    void _forEachSlot(Consumer<IPuzzleItemSlot> slotConsumer);

    IPuzzleItemSlot _getFirstEmptyItemSlot();
    int _getSlotCount();

    IPuzzleItemSlot _getSlot(int index);
    boolean _isEmpty();

    default void _clear() {
        _forEachSlot((p) -> {
            p._setItemStack(null);
        });
    }

}
