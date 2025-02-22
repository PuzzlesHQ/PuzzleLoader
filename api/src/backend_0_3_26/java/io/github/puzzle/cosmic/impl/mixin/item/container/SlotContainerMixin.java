package io.github.puzzle.cosmic.impl.mixin.item.container;

import finalforeach.cosmicreach.items.containers.SlotContainer;
import io.github.puzzle.cosmic.api.item.IPuzzleItemSlot;
import io.github.puzzle.cosmic.api.item.IPuzzleItemStack;
import io.github.puzzle.cosmic.api.item.container.IPuzzleSlotContainer;
import io.github.puzzle.cosmic.api.world.IPuzzleZone;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Internal
@Mixin(SlotContainer.class)
public class SlotContainerMixin implements IPuzzleSlotContainer {

    @Unique
    SlotContainer puzzleLoader$container = IPuzzleSlotContainer.as(this);

    @Override
    public IPuzzleSlotContainer _getBackingContainer() {
        return IPuzzleSlotContainer.as(puzzleLoader$container.getBackingContainer());
    }

    @Override
    public boolean _addItemStack(IPuzzleItemStack stack) {
        return puzzleLoader$container.addItemStack(stack.as());
    }

    @Override
    public boolean _addItemStackWithSwapGroup(IPuzzleItemStack stack) {
        return puzzleLoader$container.addItemStackWithSwapGroup(stack.as());
    }

    @Override
    public boolean _addOrMergeFrom(IPuzzleItemSlot slot) {
        return puzzleLoader$container.addOrMergeFrom(slot.as());
    }

    @Override
    public boolean _addOrMergeFrom(IPuzzleItemSlot slot, Predicate<IPuzzleItemSlot> slotPredicate) {
        return puzzleLoader$container.addOrMergeFrom(slot.as(), (s) -> slotPredicate.test(IPuzzleItemSlot.as(s)));
    }

    @Override
    public void _dropAllItems(IPuzzleZone zone, float x, float y, float z) {
        puzzleLoader$container.dropAllItems(zone.as(), x, y, z);
    }

    @Override
    public void _forEachSlot(Consumer<IPuzzleItemSlot> consumer) {
        puzzleLoader$container.forEachSlot((c) -> consumer.accept(IPuzzleItemSlot.as(c)));
    }

    @Override
    public IPuzzleItemSlot _getFirstEmptyItemSlot() {
        return IPuzzleItemSlot.as(puzzleLoader$container.getFirstEmptyItemSlot());
    }

    @Override
    public int _getSlotCount() {
        return puzzleLoader$container.getNumSlots();
    }

    @Override
    public IPuzzleItemSlot _getSlot(int i) {
        return IPuzzleItemSlot.as(puzzleLoader$container.getSlot(i));
    }

    @Override
    public boolean _isEmpty() {
        return puzzleLoader$container.isEmpty();
    }
}
