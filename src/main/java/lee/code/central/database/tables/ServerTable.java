package lee.code.central.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "server")
public class ServerTable {
  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(columnName = "spawn")
  private String spawn;

  @DatabaseField(columnName = "unique_joins")
  private int uniqueJoins;
}
