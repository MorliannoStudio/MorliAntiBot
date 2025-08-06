package morlianno.morliantibot.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.Node;

import java.util.UUID;

public class PermissionManager {
    private final LuckPerms luckPerms;

    public PermissionManager(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public void addPermission(UUID userUuid, String permission) {
        luckPerms.getUserManager().modifyUser(userUuid, user -> {
            user.data().add(Node.builder(permission).build());
            luckPerms.getUserManager().saveUser(user);
        });
    }

    public void removePermission(UUID userUuid, String permission) {
        luckPerms.getUserManager().modifyUser(userUuid, user -> {
            user.data().remove(Node.builder(permission).build());
            luckPerms.getUserManager().saveUser(user);
        });
    }
}

