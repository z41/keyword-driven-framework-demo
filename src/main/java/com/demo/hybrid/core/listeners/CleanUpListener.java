package com.demo.hybrid.core.listeners;

import com.demo.hybrid.core.ifaces.RequiresCleanUp;
import com.google.inject.spi.ProvisionListener;

import java.util.ArrayList;
import java.util.List;

public class CleanUpListener implements ProvisionListener {

    protected List<RequiresCleanUp> itemsToCleanUp = new ArrayList<>();

    public CleanUpListener(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> closeAll(itemsToCleanUp)));
    }

    private static synchronized void closeAll(List<RequiresCleanUp> items) {
        for (RequiresCleanUp item:  items){
            try {
                item.CleanUp();
            }
            catch (Exception ex){
                // sorry for no logging
                System.out.println("Caught an exception when cleaning up items:\n" + ex.getMessage());
            }
        }
    }

    @Override
    public <T> void onProvision(ProvisionInvocation<T> provisionInvocation) {
        T item = provisionInvocation.provision();
        if (item instanceof RequiresCleanUp)
        {
            itemsToCleanUp.add((RequiresCleanUp)item);
        }
    }
}
