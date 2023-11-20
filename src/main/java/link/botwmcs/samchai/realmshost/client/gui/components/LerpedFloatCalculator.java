package link.botwmcs.samchai.realmshost.client.gui.components;

import net.minecraft.util.Mth;

public class LerpedFloatCalculator {

    protected Interpolator interpolator;
    protected float previousValue;
    protected float value;

    protected Chaser chaseFunction;
    protected float chaseTarget;
    protected float chaseSpeed;
    protected boolean angularChase;

    protected boolean forcedSync;

    public LerpedFloatCalculator(Interpolator interpolator) {
        this.interpolator = interpolator;
        startWithValue(0);
        forcedSync = true;
    }

    public static LerpedFloatCalculator linear() {
        return new LerpedFloatCalculator((p, c, t) -> (float) Mth.lerp(p, c, t));
    }

    public static LerpedFloatCalculator angular() {
        LerpedFloatCalculator lerpedFloat = new LerpedFloatCalculator(LerpedFloatCalculator::angleLerp);
        lerpedFloat.angularChase = true;
        return lerpedFloat;
    }

    public LerpedFloatCalculator startWithValue(double value) {
        float f = (float) value;
        this.previousValue = f;
        this.chaseTarget = f;
        this.value = f;
        return this;
    }

    public LerpedFloatCalculator chase(double value, double speed, Chaser chaseFunction) {
        updateChaseTarget((float) value);
        this.chaseSpeed = (float) speed;
        this.chaseFunction = chaseFunction;
        return this;
    }

    public LerpedFloatCalculator disableSmartAngleChasing() {
        angularChase = false;
        return this;
    }

    public void updateChaseTarget(float target) {
        if (angularChase)
            target = value + getShortestAngleDiff(value, target);
        this.chaseTarget = target;
    }

    public boolean updateChaseSpeed(double speed) {
        float prevSpeed = this.chaseSpeed;
        this.chaseSpeed = (float) speed;
        return !Mth.equal(prevSpeed, speed);
    }

    public void tickChaser() {
        previousValue = value;
        if (chaseFunction == null)
            return;
        if (Mth.equal((double) value, chaseTarget)) {
            value = chaseTarget;
            return;
        }
        value = chaseFunction.chase(value, chaseSpeed, chaseTarget);
    }

    public void setValueNoUpdate(double value) {
        this.value = (float) value;
    }

    public void setValue(double value) {
        this.previousValue = this.value;
        this.value = (float) value;
    }

    public float getValue() {
        return getValue(1);
    }

    public float getValue(float partialTicks) {
        return interpolator.interpolate(partialTicks, previousValue, value);
    }

    public boolean settled() {
        return Mth.equal((double) previousValue, value) && (chaseFunction == null || Mth.equal((double) value, chaseTarget));
    }

    public float getChaseTarget() {
        return chaseTarget;
    }

    public void forceNextSync() {
        forcedSync = true;
    }

    @FunctionalInterface
    public interface Interpolator {
        float interpolate(double progress, double current, double target);
    }

    @FunctionalInterface
    public interface Chaser {

        Chaser IDLE = (c, s, t) -> (float) c;
        Chaser EXP = exp(Double.MAX_VALUE);
        Chaser LINEAR = (c, s, t) -> (float) (c + Mth.clamp(t - c, -s, s));

        static Chaser exp(double maxEffectiveSpeed) {
            return (c, s, t) -> (float) (c + Mth.clamp((t - c) * s, -maxEffectiveSpeed, maxEffectiveSpeed));
        }

        float chase(double current, double speed, double target);
    }

    // Util
    public static float angleLerp(double pct, double current, double target) {
        return (float) (current + getShortestAngleDiff(current, target) * pct);
    }
    public static float getShortestAngleDiff(double current, double target) {
        current = current % 360;
        target = target % 360;
        return (float) (((((target - current) % 360) + 540) % 360) - 180);
    }


}
