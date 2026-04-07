package com.steptracker.app.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DayRecordDao_Impl implements DayRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DayRecord> __insertionAdapterOfDayRecord;

  private final EntityDeletionOrUpdateAdapter<DayRecord> __updateAdapterOfDayRecord;

  public DayRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDayRecord = new EntityInsertionAdapter<DayRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `day_records` (`date`,`steps`,`activeSeconds`,`distanceMeters`,`caloriesBurned`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DayRecord entity) {
        if (entity.getDate() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getDate());
        }
        statement.bindLong(2, entity.getSteps());
        statement.bindLong(3, entity.getActiveSeconds());
        statement.bindDouble(4, entity.getDistanceMeters());
        statement.bindDouble(5, entity.getCaloriesBurned());
      }
    };
    this.__updateAdapterOfDayRecord = new EntityDeletionOrUpdateAdapter<DayRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `day_records` SET `date` = ?,`steps` = ?,`activeSeconds` = ?,`distanceMeters` = ?,`caloriesBurned` = ? WHERE `date` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DayRecord entity) {
        if (entity.getDate() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getDate());
        }
        statement.bindLong(2, entity.getSteps());
        statement.bindLong(3, entity.getActiveSeconds());
        statement.bindDouble(4, entity.getDistanceMeters());
        statement.bindDouble(5, entity.getCaloriesBurned());
        if (entity.getDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDate());
        }
      }
    };
  }

  @Override
  public Object insert(final DayRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDayRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final DayRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDayRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByDate(final String date, final Continuation<? super DayRecord> $completion) {
    final String _sql = "SELECT * FROM day_records WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DayRecord>() {
      @Override
      @Nullable
      public DayRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfActiveSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "activeSeconds");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final DayRecord _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpActiveSeconds;
            _tmpActiveSeconds = _cursor.getInt(_cursorIndexOfActiveSeconds);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getFloat(_cursorIndexOfCaloriesBurned);
            _result = new DayRecord(_tmpDate,_tmpSteps,_tmpActiveSeconds,_tmpDistanceMeters,_tmpCaloriesBurned);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<DayRecord>> getLast30Days() {
    final String _sql = "SELECT * FROM day_records ORDER BY date DESC LIMIT 30";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"day_records"}, false, new Callable<List<DayRecord>>() {
      @Override
      @Nullable
      public List<DayRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfActiveSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "activeSeconds");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final List<DayRecord> _result = new ArrayList<DayRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DayRecord _item;
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpActiveSeconds;
            _tmpActiveSeconds = _cursor.getInt(_cursorIndexOfActiveSeconds);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getFloat(_cursorIndexOfCaloriesBurned);
            _item = new DayRecord(_tmpDate,_tmpSteps,_tmpActiveSeconds,_tmpDistanceMeters,_tmpCaloriesBurned);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLast7DaysList(final Continuation<? super List<DayRecord>> $completion) {
    final String _sql = "SELECT * FROM day_records ORDER BY date DESC LIMIT 7";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DayRecord>>() {
      @Override
      @NonNull
      public List<DayRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfActiveSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "activeSeconds");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final List<DayRecord> _result = new ArrayList<DayRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DayRecord _item;
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpActiveSeconds;
            _tmpActiveSeconds = _cursor.getInt(_cursorIndexOfActiveSeconds);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final float _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getFloat(_cursorIndexOfCaloriesBurned);
            _item = new DayRecord(_tmpDate,_tmpSteps,_tmpActiveSeconds,_tmpDistanceMeters,_tmpCaloriesBurned);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object avgSteps(final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(steps) FROM day_records WHERE steps > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object avgCalories(final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(caloriesBurned) FROM day_records WHERE steps > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object totalDistanceMeters(final Continuation<? super Float> $completion) {
    final String _sql = "SELECT SUM(distanceMeters) FROM day_records";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
