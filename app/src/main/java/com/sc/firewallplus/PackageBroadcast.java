// Copyright 2014 Squario Labs

package com.sc.firewallplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver responsible for removing rules that affect uninstalled apps.
 */
public class PackageBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
			// Ignore application updates
			final boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
			if (!replacing) {
				// Update the Firewall if necessary
				final int uid = intent.getIntExtra(Intent.EXTRA_UID, -123);
				Api.applicationRemoved(context, uid);
				// Force app list reload next time
				Api.applications = null;
			}
		} else if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
			// Force app list reload next time
			Api.applications = null;
		}
	}

}
