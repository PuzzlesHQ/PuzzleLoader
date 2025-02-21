package io.github.puzzle.cosmic.api.account;

import io.github.puzzle.cosmic.api.entity.player.IPuzzlePlayer;
import io.github.puzzle.cosmic.util.ApiGen;

/**
 * An api overlay for the CosmicReach Account file.
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
    boolean _isAllowed();
    boolean _isOperator();

    IPuzzlePlayer _getPlayer();

}
