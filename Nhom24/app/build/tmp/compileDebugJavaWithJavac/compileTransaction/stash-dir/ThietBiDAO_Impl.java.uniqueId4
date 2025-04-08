package com.example.nhom24.Database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.nhom24.Model.ThietBi;
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
public final class ThietBiDAO_Impl implements ThietBiDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ThietBi> __insertionAdapterOfThietBi;

  private final EntityDeletionOrUpdateAdapter<ThietBi> __deletionAdapterOfThietBi;

  private final EntityDeletionOrUpdateAdapter<ThietBi> __updateAdapterOfThietBi;

  public ThietBiDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfThietBi = new EntityInsertionAdapter<ThietBi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `thietbi` (`id`,`maThietBi`,`tenThietBi`,`xuatXu`,`soLuong`,`loaiThietBiId`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final ThietBi entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMaThietBi() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMaThietBi());
        }
        if (entity.getTenThietBi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTenThietBi());
        }
        if (entity.getXuatXu() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getXuatXu());
        }
        statement.bindLong(5, entity.getSoLuong());
        statement.bindLong(6, entity.getLoaiThietBiId());
      }
    };
    this.__deletionAdapterOfThietBi = new EntityDeletionOrUpdateAdapter<ThietBi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `thietbi` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final ThietBi entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfThietBi = new EntityDeletionOrUpdateAdapter<ThietBi>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `thietbi` SET `id` = ?,`maThietBi` = ?,`tenThietBi` = ?,`xuatXu` = ?,`soLuong` = ?,`loaiThietBiId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final ThietBi entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMaThietBi() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMaThietBi());
        }
        if (entity.getTenThietBi() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTenThietBi());
        }
        if (entity.getXuatXu() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getXuatXu());
        }
        statement.bindLong(5, entity.getSoLuong());
        statement.bindLong(6, entity.getLoaiThietBiId());
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public void insert(final ThietBi thietBi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfThietBi.insert(thietBi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ThietBi thietBi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfThietBi.handle(thietBi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ThietBi thietBi) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfThietBi.handle(thietBi);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<ThietBi> getAll() {
    final String _sql = "SELECT * FROM thietbi";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMaThietBi = CursorUtil.getColumnIndexOrThrow(_cursor, "maThietBi");
      final int _cursorIndexOfTenThietBi = CursorUtil.getColumnIndexOrThrow(_cursor, "tenThietBi");
      final int _cursorIndexOfXuatXu = CursorUtil.getColumnIndexOrThrow(_cursor, "xuatXu");
      final int _cursorIndexOfSoLuong = CursorUtil.getColumnIndexOrThrow(_cursor, "soLuong");
      final int _cursorIndexOfLoaiThietBiId = CursorUtil.getColumnIndexOrThrow(_cursor, "loaiThietBiId");
      final List<ThietBi> _result = new ArrayList<ThietBi>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ThietBi _item;
        final String _tmpMaThietBi;
        if (_cursor.isNull(_cursorIndexOfMaThietBi)) {
          _tmpMaThietBi = null;
        } else {
          _tmpMaThietBi = _cursor.getString(_cursorIndexOfMaThietBi);
        }
        final String _tmpTenThietBi;
        if (_cursor.isNull(_cursorIndexOfTenThietBi)) {
          _tmpTenThietBi = null;
        } else {
          _tmpTenThietBi = _cursor.getString(_cursorIndexOfTenThietBi);
        }
        final String _tmpXuatXu;
        if (_cursor.isNull(_cursorIndexOfXuatXu)) {
          _tmpXuatXu = null;
        } else {
          _tmpXuatXu = _cursor.getString(_cursorIndexOfXuatXu);
        }
        final int _tmpSoLuong;
        _tmpSoLuong = _cursor.getInt(_cursorIndexOfSoLuong);
        final int _tmpLoaiThietBiId;
        _tmpLoaiThietBiId = _cursor.getInt(_cursorIndexOfLoaiThietBiId);
        _item = new ThietBi(_tmpMaThietBi,_tmpTenThietBi,_tmpXuatXu,_tmpSoLuong,_tmpLoaiThietBiId);
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
  public List<ThietBi> search(final String query) {
    final String _sql = "SELECT * FROM thietbi WHERE maThietBi LIKE ? OR tenThietBi LIKE ?";
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
      final int _cursorIndexOfMaThietBi = CursorUtil.getColumnIndexOrThrow(_cursor, "maThietBi");
      final int _cursorIndexOfTenThietBi = CursorUtil.getColumnIndexOrThrow(_cursor, "tenThietBi");
      final int _cursorIndexOfXuatXu = CursorUtil.getColumnIndexOrThrow(_cursor, "xuatXu");
      final int _cursorIndexOfSoLuong = CursorUtil.getColumnIndexOrThrow(_cursor, "soLuong");
      final int _cursorIndexOfLoaiThietBiId = CursorUtil.getColumnIndexOrThrow(_cursor, "loaiThietBiId");
      final List<ThietBi> _result = new ArrayList<ThietBi>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final ThietBi _item;
        final String _tmpMaThietBi;
        if (_cursor.isNull(_cursorIndexOfMaThietBi)) {
          _tmpMaThietBi = null;
        } else {
          _tmpMaThietBi = _cursor.getString(_cursorIndexOfMaThietBi);
        }
        final String _tmpTenThietBi;
        if (_cursor.isNull(_cursorIndexOfTenThietBi)) {
          _tmpTenThietBi = null;
        } else {
          _tmpTenThietBi = _cursor.getString(_cursorIndexOfTenThietBi);
        }
        final String _tmpXuatXu;
        if (_cursor.isNull(_cursorIndexOfXuatXu)) {
          _tmpXuatXu = null;
        } else {
          _tmpXuatXu = _cursor.getString(_cursorIndexOfXuatXu);
        }
        final int _tmpSoLuong;
        _tmpSoLuong = _cursor.getInt(_cursorIndexOfSoLuong);
        final int _tmpLoaiThietBiId;
        _tmpLoaiThietBiId = _cursor.getInt(_cursorIndexOfLoaiThietBiId);
        _item = new ThietBi(_tmpMaThietBi,_tmpTenThietBi,_tmpXuatXu,_tmpSoLuong,_tmpLoaiThietBiId);
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
