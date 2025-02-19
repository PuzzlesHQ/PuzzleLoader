package io.github.puzzle.cosmic.impl.mixin.account;

import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.accounts.Account;
import finalforeach.cosmicreach.networking.server.ServerSingletons;
import io.github.puzzle.cosmic.api.account.IPuzzleAccount;
import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Account.class)
public abstract class AccountMixin implements IPuzzleAccount {

    @Shadow private String username;

    @Shadow private String uniqueId;

    @Override
    public String _getUsername() {
        return username;
    }

    @Override
    public String _getUniqueId() {
        return uniqueId;
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
