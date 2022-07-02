package ch7.observer;

public class Nasa implements LandingObserver {
    @Override
    public void observeLanding(String name) {
        if (name.contains("Asteroid")){
            System.out.println("Good job landing on " + name);
        }
    }
}
