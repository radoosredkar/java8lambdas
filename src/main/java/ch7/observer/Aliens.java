package ch7.observer;

public class Aliens implements LandingObserver {
    @Override
    public void observeLanding(String name) {
        if (name.contains("Apollo")){
            System.out.println("They are distracted, let's invade Earth!");
        }
    }
}
