package io.github.puzzle.cosmic.api.account;

import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.util.ApiDeclaration;

@ApiDeclaration(api = IPuzzleAccount.class, impl = "Account")
public interface IPuzzleAccount {

    String _getDisplayName();
    String _getUsername();
    String _getUniqueId();

    void _setUsername(String username);
    void _setUniqueId(String uniqueId);

    String _getPrefix();
    String _getDebugString();

    boolean _canSave();
    boolean _isAllowed();
    boolean _isOperator();

    IPuzzlePlayer _getPlayer();

}
