package io.github.puzzle.cosmic.impl.mixin.account;

import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import io.github.puzzle.cosmic.api.account.IPuzzleAccount;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.util.annotation.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Internal
@Mixin(Account.class)
public abstract class AccountMixin implements IPuzzleAccount {

    @Unique
    private final transient Account puzzleLoader$account = IPuzzleAccount.as(this);

    @Override
    public String _getUsername() {
        return puzzleLoader$account.getUsername();
    }

    @Override
    public String _getUniqueId() {
        return puzzleLoader$account.getUniqueId();
    }

    public IPuzzlePlayer _getPlayer() {
        if (GameSingletons.isHost && GameSingletons.isClient) {
            GameSingletons.client().getAccount();
        }
        return IPuzzlePlayer.as(GameSingletons.getPlayerFromAccount(IPuzzleAccount.as(this)));
    }

    public boolean _isOperator() {
        return GameSingletons.isHost && GameSingletons.isClient || ServerSingletons.OP_LIST.hasAccount(IPuzzleAccount.as(this));
    }
}
