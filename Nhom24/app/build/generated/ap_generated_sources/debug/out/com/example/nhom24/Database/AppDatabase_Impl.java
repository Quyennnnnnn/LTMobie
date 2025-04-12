package com.example.nhom24.Database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.nhom24.DAO.LoaiThietBiDAO;
import com.example.nhom24.DAO.LoaiThietBiDAO_Impl;
import com.example.nhom24.DAO.ThietBiDAO;
import com.example.nhom24.DAO.ThietBiDAO_Impl;
import com.example.nhom24.DAO.UserDAO;
import com.example.nhom24.DAO.UserDAO_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDAO _userDAO;

  private volatile LoaiThietBiDAO _loaiThietBiDAO;

  private volatile ThietBiDAO _thietBiDAO;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `user` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `phone` TEXT, `email` TEXT, `password` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `loaithietbi` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mathietbi` TEXT, `tenthietbi` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `thietbi` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `maThietBi` TEXT, `tenThietBi` TEXT, `xuatXu` TEXT, `soLuong` INTEGER NOT NULL, `tinhTrang` TEXT, `imageUrl` TEXT, `loaiThietBiId` INTEGER NOT NULL, FOREIGN KEY(`loaiThietBiId`) REFERENCES `loaithietbi`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7bff662e660a98cf22a84ffd45bd4010')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `user`");
        db.execSQL("DROP TABLE IF EXISTS `loaithietbi`");
        db.execSQL("DROP TABLE IF EXISTS `thietbi`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUser = new HashMap<String, TableInfo.Column>(4);
        _columnsUser.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("phone", new TableInfo.Column("phone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("password", new TableInfo.Column("password", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUser = new TableInfo("user", _columnsUser, _foreignKeysUser, _indicesUser);
        final TableInfo _existingUser = TableInfo.read(db, "user");
        if (!_infoUser.equals(_existingUser)) {
          return new RoomOpenHelper.ValidationResult(false, "user(com.example.nhom24.Model.User).\n"
                  + " Expected:\n" + _infoUser + "\n"
                  + " Found:\n" + _existingUser);
        }
        final HashMap<String, TableInfo.Column> _columnsLoaithietbi = new HashMap<String, TableInfo.Column>(3);
        _columnsLoaithietbi.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoaithietbi.put("mathietbi", new TableInfo.Column("mathietbi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLoaithietbi.put("tenthietbi", new TableInfo.Column("tenthietbi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLoaithietbi = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLoaithietbi = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLoaithietbi = new TableInfo("loaithietbi", _columnsLoaithietbi, _foreignKeysLoaithietbi, _indicesLoaithietbi);
        final TableInfo _existingLoaithietbi = TableInfo.read(db, "loaithietbi");
        if (!_infoLoaithietbi.equals(_existingLoaithietbi)) {
          return new RoomOpenHelper.ValidationResult(false, "loaithietbi(com.example.nhom24.Model.LoaiThietBi).\n"
                  + " Expected:\n" + _infoLoaithietbi + "\n"
                  + " Found:\n" + _existingLoaithietbi);
        }
        final HashMap<String, TableInfo.Column> _columnsThietbi = new HashMap<String, TableInfo.Column>(8);
        _columnsThietbi.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("maThietBi", new TableInfo.Column("maThietBi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("tenThietBi", new TableInfo.Column("tenThietBi", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("xuatXu", new TableInfo.Column("xuatXu", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("soLuong", new TableInfo.Column("soLuong", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("tinhTrang", new TableInfo.Column("tinhTrang", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThietbi.put("loaiThietBiId", new TableInfo.Column("loaiThietBiId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysThietbi = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysThietbi.add(new TableInfo.ForeignKey("loaithietbi", "CASCADE", "NO ACTION", Arrays.asList("loaiThietBiId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesThietbi = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoThietbi = new TableInfo("thietbi", _columnsThietbi, _foreignKeysThietbi, _indicesThietbi);
        final TableInfo _existingThietbi = TableInfo.read(db, "thietbi");
        if (!_infoThietbi.equals(_existingThietbi)) {
          return new RoomOpenHelper.ValidationResult(false, "thietbi(com.example.nhom24.Model.ThietBi).\n"
                  + " Expected:\n" + _infoThietbi + "\n"
                  + " Found:\n" + _existingThietbi);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7bff662e660a98cf22a84ffd45bd4010", "2ed502dca1675f03f999ae4d2f03800e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "user","loaithietbi","thietbi");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `user`");
      _db.execSQL("DELETE FROM `loaithietbi`");
      _db.execSQL("DELETE FROM `thietbi`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDAO.class, UserDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(LoaiThietBiDAO.class, LoaiThietBiDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(ThietBiDAO.class, ThietBiDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDAO userDAO() {
    if (_userDAO != null) {
      return _userDAO;
    } else {
      synchronized(this) {
        if(_userDAO == null) {
          _userDAO = new UserDAO_Impl(this);
        }
        return _userDAO;
      }
    }
  }

  @Override
  public LoaiThietBiDAO loaiThietBiDAO() {
    if (_loaiThietBiDAO != null) {
      return _loaiThietBiDAO;
    } else {
      synchronized(this) {
        if(_loaiThietBiDAO == null) {
          _loaiThietBiDAO = new LoaiThietBiDAO_Impl(this);
        }
        return _loaiThietBiDAO;
      }
    }
  }

  @Override
  public ThietBiDAO thietBiDAO() {
    if (_thietBiDAO != null) {
      return _thietBiDAO;
    } else {
      synchronized(this) {
        if(_thietBiDAO == null) {
          _thietBiDAO = new ThietBiDAO_Impl(this);
        }
        return _thietBiDAO;
      }
    }
  }
}
