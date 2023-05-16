package mycraft.survival;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public final class Survival extends JavaPlugin implements Listener {
    class setItem{
        ItemStack item;

        Inventory i;
        public void setItem(String v){
            item = new ItemStack(Material.valueOf(v));

        }
        public void getInventoryname(Inventory i){
            this.i = i;
        }

        public void setItemInInventory(int num){

            i.setItem(num, item);
        }

        public void informationOfItem(String name, String explain) {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(name);
            itemMeta.setLore(Arrays.asList(explain));
            item.setItemMeta(itemMeta);
        }
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void clickinventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = Bukkit.createInventory(null, 27, "개인 창고");

        if (e.getView().getTitle() == "메뉴") {
            if (e.getCurrentItem().getType() == Material.CLOCK) {
                e.setCancelled(true);
                if (p.getWorld().getTime() >= 12516) {
                    p.getWorld().setTime(0);
                    p.sendMessage("낮이 되었습니다");
                } else {
                    p.getWorld().setTime(12516);
                    p.sendMessage("밤이 되었습니다");
                }
            }
            if (e.getCurrentItem().getType() == Material.LIGHTNING_ROD) {
                e.setCancelled(true);
                p.getWorld().setStorm(false);
                p.sendMessage("비가 그쳤습니다");
            }
            if (e.getCurrentItem().getType() == Material.CHEST) {
                e.setCancelled(true);
                p.openInventory(inv);
            }
            if (e.getCurrentItem().getType() == Material.ENDER_EYE) {
                e.setCancelled(true);
                p.sendMessage("실행됨");
                p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(999999999, 0));
                p.addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(999999999, 0));
            }
        }
    }

    @EventHandler
    public void a182374(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        Inventory i = Bukkit.createInventory(null, 27, "메뉴");

        setItem s1 = new setItem();
        s1.getInventoryname(i);
        s1.setItem("CLOCK");
        s1.informationOfItem("낮밤 바꾸기", "누르면 낮 또는 밤으로 바꿉니다");
        s1.setItemInInventory(11);


        ItemStack L_rod = new ItemStack(Material.LIGHTNING_ROD);
        ItemMeta L_rodMeta = L_rod.getItemMeta();
        L_rodMeta.setDisplayName("날씨 바꾸기");
        L_rodMeta.setLore(Arrays.asList("누르면 맑게 하거나 비가 오게 만듭니다"));
        L_rod.setItemMeta(L_rodMeta);

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("창고");
        chestMeta.setLore(Arrays.asList("누르면 개인 창고가 열립니다"));
        chest.setItemMeta(chestMeta);



        i.setItem(13, L_rod);
        i.setItem(15, chest);

        if(p.isSneaking()){
            p.openInventory(i);
        }
    }
}
