package lee.code.central.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "players")
public class PlayerTable {
  @DatabaseField(id = true, canBeNull = false)
  private UUID uniqueId;

  @DatabaseField(columnName = "flying")
  private boolean flying;

  @DatabaseField(columnName = "god")
  private boolean god;

  @DatabaseField(columnName = "mail")
  private String mail;

  public PlayerTable(UUID uniqueId) {
    this.uniqueId = uniqueId;
  }
}
