package com.example.nhom24.DAO;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.nhom24.Model.LoaiThietBi;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LoaiThietBiDAO_Impl implements LoaiThietBiDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LoaiThietBi> __insertionAdapterOfLoaiThietBi;

  private final EntityDeletionOrUpdateAdapter<LoaiThietBi> __deletionAdapterOfLoaiThietBi;

  private final EntityDeletionOrUpdateAdapter<LoaiThietBi> __updateAdapterOfLoaiThietBi;

  public LoaiThietBiDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoaiThietBi = new EntityInsertionAdapter<LoaiThietBi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `loaithietbi` (`id`,`mathietbi`,`tenthietbi`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoaiThietBi entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMathietbi() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMathietbi());
        }
        if (entity.getTenthietbi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTenthietbi());
        }
      }
    };
    this.__deletionAdapterOfLoaiThietBi = new EntityDeletionOrUpdateAdapter<LoaiThietBi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `loaithietbi` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoaiThietBi entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfLoaiThietBi = new EntityDeletionOrUpdateAdapter<LoaiThietBi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `loaithietbi` SET `id` = ?,`mathietbi` = ?,`tenthietbi` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final LoaiThietBi entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMathietbi() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMathietbi());
        }
        if (entity.getTenthietbi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTenthietbi());
        }
        statement.bindLong(4, entity.getId());
      }
    };
  }

  @Override
  public void insert(final LoaiThietBi loaiThietBi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLoaiThietBi.insert(loaiThietBi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final LoaiThietBi loaiThietBi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfLoaiThietBi.handle(loaiThietBi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final LoaiThietBi loaiThietBi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLoaiThietBi.handle(loaiThietBi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<LoaiThietBi> getAll() {
    final String _sql = "SELECT * FROM loaithietbi";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMathietbi = CursorUtil.getColumnIndexOrThrow(_cursor, "mathietbi");
      final int _cursorIndexOfTenthietbi = CursorUtil.getColumnIndexOrThrow(_cursor, "tenthietbi");
      final List<LoaiThietBi> _result = new ArrayList<LoaiThietBi>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoaiThietBi _item;
        final String _tmpMathietbi;
        if (_cursor.isNull(_cursorIndexOfMathietbi)) {
          _tmpMathietbi = null;
        } else {
          _tmpMathietbi = _cursor.getString(_cursorIndexOfMathietbi);
        }
        final String _tmpTenthietbi;
        if (_cursor.isNull(_cursorIndexOfTenthietbi)) {
          _tmpTenthietbi = null;
        } else {
          _tmpTenthietbi = _cursor.getString(_cursorIndexOfTenthietbi);
        }
        _item = new LoaiThietBi(_tmpMathietbi,_tmpTenthietbi);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LoaiThietBi> search(final String query) {
    final String _sql = "SELECT * FROM loaithietbi WHERE mathietbi LIKE ? OR tenthietbi LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMathietbi = CursorUtil.getColumnIndexOrThrow(_cursor, "mathietbi");
      final int _cursorIndexOfTenthietbi = CursorUtil.getColumnIndexOrThrow(_cursor, "tenthietbi");
      final List<LoaiThietBi> _result = new ArrayList<LoaiThietBi>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoaiThietBi _item;
        final String _tmpMathietbi;
        if (_cursor.isNull(_cursorIndexOfMathietbi)) {
          _tmpMathietbi = null;
        } else {
          _tmpMathietbi = _cursor.getString(_cursorIndexOfMathietbi);
        }
        final String _tmpTenthietbi;
        if (_cursor.isNull(_cursorIndexOfTenthietbi)) {
          _tmpTenthietbi = null;
        } else {
          _tmpTenthietbi = _cursor.getString(_cursorIndexOfTenthietbi);
        }
        _item = new LoaiThietBi(_tmpMathietbi,_tmpTenthietbi);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
