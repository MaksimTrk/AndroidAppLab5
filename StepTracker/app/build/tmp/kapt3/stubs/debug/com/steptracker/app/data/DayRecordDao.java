package com.steptracker.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\r0\fH\'J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0015"}, d2 = {"Lcom/steptracker/app/data/DayRecordDao;", "", "avgCalories", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "avgSteps", "getByDate", "Lcom/steptracker/app/data/DayRecord;", "date", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLast30Days", "Landroidx/lifecycle/LiveData;", "", "getLast7DaysList", "insert", "", "record", "(Lcom/steptracker/app/data/DayRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "totalDistanceMeters", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface DayRecordDao {
    
    @androidx.room.Query(value = "SELECT * FROM day_records WHERE date = :date LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.steptracker.app.data.DayRecord> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM day_records ORDER BY date DESC LIMIT 30")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.steptracker.app.data.DayRecord>> getLast30Days();
    
    @androidx.room.Query(value = "SELECT * FROM day_records ORDER BY date DESC LIMIT 7")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLast7DaysList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.steptracker.app.data.DayRecord>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.steptracker.app.data.DayRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.steptracker.app.data.DayRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT AVG(steps) FROM day_records WHERE steps > 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object avgSteps(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Float> $completion);
    
    @androidx.room.Query(value = "SELECT AVG(caloriesBurned) FROM day_records WHERE steps > 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object avgCalories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Float> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(distanceMeters) FROM day_records")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object totalDistanceMeters(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Float> $completion);
}