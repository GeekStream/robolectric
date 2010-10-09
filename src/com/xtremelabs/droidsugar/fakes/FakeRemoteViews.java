package com.xtremelabs.droidsugar.fakes;

import android.app.PendingIntent;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.xtremelabs.droidsugar.util.Implementation;
import com.xtremelabs.droidsugar.util.Implements;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration"})
@Implements(RemoteViews.class)
public class FakeRemoteViews {
    private List<ViewUpdater> viewUpdaters = new ArrayList<ViewUpdater>();

    @Implementation
    public void setTextViewText(int viewId, final CharSequence text) {
        viewUpdaters.add(new ViewUpdater(viewId) {
            @Override public void doUpdate(View view) {
                ((TextView) view).setText(text);
            }
        });
    }

    @Implementation
    public void setOnClickPendingIntent(int viewId, final PendingIntent pendingIntent) {
        viewUpdaters.add(new ViewUpdater(viewId) {
            @Override void doUpdate(final View view) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        try {
                            pendingIntent.send(view.getContext(), 0, null);
                        } catch (PendingIntent.CanceledException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
    }

    @Implementation
    public void reapply(Context context, View v) {
        for (ViewUpdater viewUpdater : viewUpdaters) {
            viewUpdater.update(v);
        }
    }

    @Implementation
    public void setViewVisibility(int viewId, final int visibility) {
        viewUpdaters.add(new ViewUpdater(viewId) {
            @Override public void doUpdate(View view) {
                view.setVisibility(visibility);
            }
        });
    }

    private abstract class ViewUpdater {
        private int viewId;

        public ViewUpdater(int viewId) {
            this.viewId = viewId;
        }

        final void update(View parent) {
            doUpdate(parent.findViewById(viewId));
        }

        abstract void doUpdate(View view);
    }
}
