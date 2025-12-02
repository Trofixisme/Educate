package com.group.InternMap.Repo;

public class ShutDownSaver {


        public static void registerShutdownHook() {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\u001B[33mShutting down... Saving all repositories.\u001B[0m");
                RepositoryAccessors.saveAll();
            }));
        }
    }


