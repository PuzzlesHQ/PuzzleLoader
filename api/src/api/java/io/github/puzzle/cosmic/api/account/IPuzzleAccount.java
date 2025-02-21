package io.github.puzzle.cosmic.api.account;

import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.util.annotation.compile.ApiGen;

/**
 *
 * @author Mr_Zombii
 * @since 0.3.26
 */
@ApiGen("Account")
public interface IPuzzleAccount {

    /**
     * Gets the display name of the account
     */
    String _getDisplayName();

    /**
     * Gets the username of the account.
     */
    String _getUsername();

    /**
     * Gets the unique id of the account
     */
    String _getUniqueId();

    /**
     * Sets the username of the account.
     */
    void _setUsername(String username);

    /**
     * Sets the unique id of the account.
     */
    void _setUniqueId(String uniqueId);

    /**
     * Returns the account type prefix.
     */
    String _getPrefix();

    /**
     * Gets the debug string for the account.
     */
    String _getDebugString();

    /**
     * Checks the ability to save the account.
     */
    boolean _canSave();

    /**
     * Checks if the type of account is allowed on a server.
     */
    boolean _isAllowed();

    /**
     * Checks if an operator player owns this account.
     */
    boolean _isOperator();

    /**
     * Gets the player who owns this account.
     */
    IPuzzlePlayer _getPlayer();

}
