package engine.entity;

import engine.Timer;
import engine.Window;
import game.StarWars;

public class GameEngine implements Runnable {

    private static final int TARGET_FPS = 75;
    private static final int TARGET_UPS = 30;
    private final Window window;
    private final Thread gameLoopThread;
    private final Timer timer;
    private final StarWars starWars;

    public GameEngine(String windowTitle, int width, int height, boolean vSync, StarWars starWars) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vSync);
        this.starWars = starWars;
        timer = new Timer();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void init() throws Exception {
        window.init();
        timer.init();
        starWars.init();
    }

    private void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        while (!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if ( !window.isvSync() ) {
                sync();
            }
        }
    }

    private void cleanup() {
        starWars.cleanup();
    }
    
    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void input() {
        starWars.input(window);
    }

    protected void update(float interval) {
        starWars.update(interval);
    }

    private void render() {
        starWars.render(window);
        window.update();
    }
}
