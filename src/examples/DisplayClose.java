package examples;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class DisplayClose implements Runnable {

    Component component;
    int favoriteWidth;
    int favoriteHeight;
    int widthGrowth;
    int heightGrowth;
    Dimension screenSize;

    public DisplayClose(Component component, int favoriteWidth, int favoriteHeight) {
        this.component = component;
        this.favoriteWidth = favoriteWidth;
        this.favoriteHeight = favoriteHeight;
        getScreenSize();
    }

    private void gotoCenter() {
        component.setLocation(((int) screenSize.getWidth() - component.getWidth()) / 2, ((int) screenSize.getHeight() - component.getHeight()) / 2 - 25);
    }

    private void getScreenSize() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void run() {
        try {
            if (favoriteWidth < component.getWidth()) {
                widthGrowth = -1;
            } else {
                widthGrowth = 1;
            }
            if (favoriteHeight < component.getHeight()) {
                heightGrowth = -1;
            } else {
                heightGrowth = 1;
            }
            int oldWidth = component.getWidth();
            int oldHeigth = component.getHeight();
            int widthRange = Math.abs(favoriteWidth - oldWidth);
            int heightRange = Math.abs(favoriteHeight - oldHeigth);
            int A = 2 * widthRange + 2;
            int B = 2 * heightRange + 2;
            double halfTime = 1000;
            int delay = 15;
            int speed = 15;



            double T = A * halfTime / 1000;
            oldWidth = component.getWidth();
            for (double t = 0; t < halfTime; t = t + speed) {
                double SX = T * t / 1000 - A * (t / 1000) * (t / 1000) / 2;
                component.setSize(oldWidth + (int) SX * widthGrowth, component.getHeight());
                gotoCenter();
                Thread.sleep(delay);
            }
            double v0Y = B * halfTime / 1000;
            oldHeigth = component.getHeight();
            for (double t = 0; t < halfTime; t = t + speed) {
                double SY = v0Y * t / 1000 - B * (t / 1000) * (t / 1000) / 2;
                component.setSize(component.getWidth(), oldHeigth + (int) SY * heightGrowth);
                gotoCenter();
                Thread.sleep(delay);
            }

            component.setSize(favoriteWidth, favoriteHeight);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}