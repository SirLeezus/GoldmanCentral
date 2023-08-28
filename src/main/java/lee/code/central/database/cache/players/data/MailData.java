package lee.code.central.database.cache.players.data;

import lee.code.central.database.cache.players.CachePlayers;
import lee.code.central.database.tables.PlayerTable;
import lee.code.central.utils.ItemUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MailData {
  private final CachePlayers cachePlayers;
  private final ConcurrentHashMap<UUID, ConcurrentHashMap<Integer, String>> mailCache = new ConcurrentHashMap<>();

  public MailData(CachePlayers cachePlayers) {
    this.cachePlayers = cachePlayers;
  }

  public int getBookAmount(UUID uuid) {
    if (!mailCache.containsKey(uuid)) return 0;
    return mailCache.get(uuid).size();
  }

  private int getNextBookID(UUID uuid) {
    if (!mailCache.containsKey(uuid)) return 1;
    final Optional<Integer> highestKey = getMail(uuid).keySet().stream().max(Integer::compareTo);
    return highestKey.map(integer -> integer + 1).orElse(1);
  }

  private void removeMailCache(UUID uuid, int id) {
    mailCache.get(uuid).remove(id);
    if (mailCache.get(uuid).isEmpty()) mailCache.remove(uuid);
  }

  private void setMailCache(UUID uuid, int id, String book) {
    if (mailCache.containsKey(uuid)) {
      mailCache.get(uuid).put(id, book);
    } else {
      final ConcurrentHashMap<Integer, String> mail = new ConcurrentHashMap<>();
      mail.put(id, book);
      mailCache.put(uuid, mail);
    }
  }

  private ConcurrentHashMap<Integer, String> getMail(UUID uuid) {
    return mailCache.get(uuid);
  }

  public boolean hasMail(UUID uuid) {
    return mailCache.containsKey(uuid);
  }

  public List<Integer> getAllMailIDs(UUID uuid) {
    return new ArrayList<>(mailCache.get(uuid).keySet());
  }

  public String getMailBookString(UUID uuid, int id) {
    return mailCache.get(uuid).get(id);
  }

  public ItemStack getMailBook(UUID uuid, int id) {
    return ItemUtil.parseItemStack(mailCache.get(uuid).get(id));
  }

  public List<ItemStack> getAllMailBooks(UUID uuid) {
    if (!mailCache.containsKey(uuid)) return new ArrayList<>();
    final List<ItemStack> books = new ArrayList<>();
    for (String book : getMail(uuid).values()) {
      books.add(ItemUtil.parseItemStack(book));
    }
    return books;
  }

  public void cacheMail(PlayerTable playerTable) {
    if (playerTable.getMail() == null) return;
    final String[] allMail = playerTable.getMail().split(",");
    for (String mail : allMail) {
      final String[] mailData = mail.split("-");
      setMailCache(playerTable.getUniqueId(), Integer.parseInt(mailData[0]), mailData[1]);
    }
  }

  public void addMail(UUID uuid, ItemStack bookItem) {
    final String book = ItemUtil.serializeItemStack(bookItem);
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    final int id = getNextBookID(uuid);
    if (playerTable.getMail() == null) playerTable.setMail(id + "-" + book);
    else playerTable.setMail(playerTable.getMail() + "," + id + "-" + book);
    setMailCache(uuid, id, book);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public void removeMail(UUID uuid, int id) {
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    final Set<String> allMail = Collections.synchronizedSet(new HashSet<>(List.of(playerTable.getMail().split(","))));
    allMail.remove(id + "-" + getMailBookString(uuid, id));
    if (allMail.isEmpty()) playerTable.setMail(null);
    else playerTable.setMail(StringUtils.join(allMail, ","));
    removeMailCache(uuid, id);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

}
