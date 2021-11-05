/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client.notifications;

import com.europa.client.modules.client.ModuleNotifications;
import com.europa.client.modules.client.notifications.Notification;
import java.util.ArrayList;

public class NotificationProcessor {
    public ArrayList<Notification> notifications = new ArrayList();

    public void handleNotifications(int posY) {
        for (int i = 0; i < this.getNotifications().size(); ++i) {
            if (this.getNotifications().get(i).animationUtils2.isDone() && !this.getNotifications().get(i).didThing) {
                this.getNotifications().get(i).animationUtils.reset();
                this.getNotifications().get(i).didThing = true;
            }
            if (this.getNotifications().get(i).animationUtils.isDone() && !this.getNotifications().get(i).isReversing && this.getNotifications().get(i).timer.hasReached(this.getNotifications().get(i).disableTime - ModuleNotifications.inOutTime.getValue().intValue() * 2)) {
                this.getNotifications().get(i).reverse.reset();
                this.getNotifications().get(i).reverse2.reset();
                this.getNotifications().get(i).isReversing = true;
            }
            if (this.getNotifications().get(i).isReversing && this.getNotifications().get(i).reverse.isDone() && !this.getNotifications().get(i).didFirstReverse) {
                this.getNotifications().get(i).reverse2.reset();
                this.getNotifications().get(i).didFirstReverse = true;
            }
            this.getNotifications().get(i).onDraw(posY);
            if (ModuleNotifications.addType.getValue()) {
                posY += 22;
            }
            else {
                posY -= 22;
            }
        }
    }

    public void addNotification(final String text, final long duration, final long inOutTime) {
        this.getNotifications().add(new Notification(text, duration, inOutTime));
    }

    public ArrayList<Notification> getNotifications() {
        return this.notifications;
    }
}

