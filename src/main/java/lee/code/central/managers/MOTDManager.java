package lee.code.central.managers;

import lee.code.central.Central;
import lee.code.central.utils.CoreUtil;
import lee.code.central.utils.VariableUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MOTDManager {
  private final Central central;
  private final ArrayList<Component> lines = new ArrayList<>();

  public MOTDManager(Central central) {
    this.central = central;
    loadAndStoreFileData();
  }

  private void loadAndStoreFileData() {
    final File file = new File(central.getDataFolder(), "motd.txt");
    createFile(file);
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(VariableUtil.parseVariables(CoreUtil.parseColorComponent(line)));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createFile(File file) {
    if (!central.getDataFolder().exists()) central.getDataFolder().mkdir();
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void sendMOTD(Player player) {
    for (Component line : lines) player.sendMessage(line);
  }
}
