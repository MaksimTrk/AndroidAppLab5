package com.steptracker.app.sensor;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001Bh\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012K\u0010\u0007\u001aG\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0002\u0010\u000fJ\u0006\u0010\u001f\u001a\u00020\u001cJ\u0006\u0010 \u001a\u00020\u001cJ\u001a\u0010!\u001a\u00020\u00062\b\u0010\"\u001a\u0004\u0018\u00010\u00112\u0006\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\'H\u0016J\u0006\u0010(\u001a\u00020\u0006J\u0006\u0010)\u001a\u00020\u0006R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000RS\u0010\u0007\u001aG\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00060\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006*"}, d2 = {"Lcom/steptracker/app/sensor/StepSensor;", "Landroid/hardware/SensorEventListener;", "context", "Landroid/content/Context;", "onStep", "Lkotlin/Function0;", "", "onAccel", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "x", "y", "z", "(Landroid/content/Context;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;)V", "accelerometer", "Landroid/hardware/Sensor;", "lastMag", "", "lastStepTime", "", "magBuffer", "Lkotlin/collections/ArrayDeque;", "sensorManager", "Landroid/hardware/SensorManager;", "stepDetector", "usingStepDetector", "", "getUsingStepDetector", "()Z", "hasAccelerometer", "hasSteDetector", "onAccuracyChanged", "sensor", "accuracy", "", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "register", "unregister", "app_debug"})
public final class StepSensor implements android.hardware.SensorEventListener {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function0<kotlin.Unit> onStep = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function3<java.lang.Float, java.lang.Float, java.lang.Float, kotlin.Unit> onAccel = null;
    @org.jetbrains.annotations.NotNull()
    private final android.hardware.SensorManager sensorManager = null;
    @org.jetbrains.annotations.Nullable()
    private final android.hardware.Sensor stepDetector = null;
    @org.jetbrains.annotations.Nullable()
    private final android.hardware.Sensor accelerometer = null;
    private double lastMag = 0.0;
    private long lastStepTime = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.collections.ArrayDeque<java.lang.Double> magBuffer = null;
    
    public StepSensor(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStep, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Float, ? super java.lang.Float, ? super java.lang.Float, kotlin.Unit> onAccel) {
        super();
    }
    
    private final boolean getUsingStepDetector() {
        return false;
    }
    
    public final void register() {
    }
    
    public final void unregister() {
    }
    
    @java.lang.Override()
    public void onSensorChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override()
    public void onAccuracyChanged(@org.jetbrains.annotations.Nullable()
    android.hardware.Sensor sensor, int accuracy) {
    }
    
    public final boolean hasSteDetector() {
        return false;
    }
    
    public final boolean hasAccelerometer() {
        return false;
    }
}