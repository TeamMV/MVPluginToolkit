package dev.mv.ptk.keyboard;

import dev.mv.ptk.PluginListener;
import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.InventoryInterface;
import dev.mv.ptk.utils.HeadUtils;
import dev.mv.ptk.utils.input.InvClickReceiver;
import dev.mv.ptk.utils.input.TextProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SoftKeyboard extends TextProvider implements InvClickReceiver {
    private static String letters = "abcdefghijklmnopqrstuvwxyz";
    private static String[] letterUrls = {
            "http://textures.minecraft.net/texture/616d6db314fcba91d727d21c8dedf4d698d73d0e717d5430f789f69e16fb6f84",
            "http://textures.minecraft.net/texture/79e5ca99fe0f0fd66acbe096c3e6dafb2bce7c83a9c10f4d86c4be411dbbe1b2",
            "http://textures.minecraft.net/texture/dbbb16ab02b576fb3061207a2394a1e654fe1108e51c3d7b7cc89c1e6c529d98",
            "http://textures.minecraft.net/texture/25551f34c45fb1811e4cc2fa8ec371e45ba0977e1d1521120f0f57560f73f590",
            "http://textures.minecraft.net/texture/98a1cfdd4fbf097779b2fb878bfedbb9c8c2b7d04309b66d5a504d0c4dfe2216",
            "http://textures.minecraft.net/texture/37c1e5e55fe0bab6f3cdff3a88e0a3b61df3626c40a6dcfd1cd68cacc5e6b488",
            "http://textures.minecraft.net/texture/43cc08284720b9d4a57fbf6a1c9c6bad8023b71dadf0f9d8327262c7b19477dd",
            "http://textures.minecraft.net/texture/3357711328374a477185fd5609ee41bee22c91569f41fce6bf92f4d6ecd9ecd2",
            "http://textures.minecraft.net/texture/9c6cab6b3cbbafc9af7ab3a5d689be96c5da90eb8d33aa30ece766c05fefbe05",
            "http://textures.minecraft.net/texture/a6912351c95e59ffda5913f69a41d26e5b8b58bd8ac0c575cd57e8564eb0d8cf",
            "http://textures.minecraft.net/texture/2b6511c64525fa0d63ad3cddeec7e37ba9ea68a6b8eb3bc6bdb525fff3ad882d",
            "http://textures.minecraft.net/texture/2136d33648ff26ceabae59338e725551eea1a1f7bb02f8bba66cd43309313067",
            "http://textures.minecraft.net/texture/acefe5784e7d8b9eeb444c3264ee142bbc10c82ef817193e061b64a28c789cf0",
            "http://textures.minecraft.net/texture/7fa40b69e2bc6331c892af0d71fccde4d2b2ecaf511ff4faf1edd4fea0eb2878",
            "http://textures.minecraft.net/texture/de0ea35a41ccb7ac9be51bbef94e5e7d3cde30d7b93f5d8f36cccbddf83d771",
            "http://textures.minecraft.net/texture/14c11812c3e1d3332a4da23460e5a28ea658082b028d50fd2884c05ffa008f53",
            "http://textures.minecraft.net/texture/135061959a20d96963846cfa2ad82abb6617de0696de951e0d4a128b487c54ff",
            "http://textures.minecraft.net/texture/95168a854fccd32afda6d0b8ed8bbe0a8227a09eaf3687c92868b3066aaa5d49",
            "http://textures.minecraft.net/texture/a70ae2255ac8cbda7444fca8ee62d8e57841eefdc9c381a840c87470c3f499fd",
            "http://textures.minecraft.net/texture/615188d37518dba97290dafc77769e702a04a3c18a7bc6326e448ee15dbc54d9",
            "http://textures.minecraft.net/texture/d6c445a6e8cd94cd52b4099e95a5068851bf59306810039046481e3e5a2937f6",
            "http://textures.minecraft.net/texture/f21f73ccb04a7eb3e9c302bc994c1f0144959fc03eac531f006351aeb18237fd",
            "http://textures.minecraft.net/texture/26bbf94a224be9f8ffd49eaed6bd036e483d565f6b411dc6547629a257f755ae",
            "http://textures.minecraft.net/texture/91182931670d3a2b2abf5331f9f022be05f795111e75ec46f32db76de39d8b43",
            "http://textures.minecraft.net/texture/109d09d1d9f285dd0da5de63395d8e2f7065809543b3566a488b49dc3a198a81",
            "http://textures.minecraft.net/texture/6b6180de7ce1cdabe1e8d36299b36ab87070f9f05bade1c029e3d471568e0504"
    };

    private static ItemStack[] letterHeads = new ItemStack[letterUrls.length];

    static {
        for (int i = 0; i < letterHeads.length; i++) {
            letterHeads[i] = HeadUtils.getHead(letterUrls[i], String.valueOf(letters.charAt(i)));
        }
    }

    private static String spaceUrl = "http://textures.minecraft.net/texture/2014cd0b76fdf1f894eb4afce9a4bc91eab77a09dee9048b680b2fa5a52f2528";
    private static ItemStack spaceHead = HeadUtils.getHead(spaceUrl, "space");

    private static String deleteUrl = "http://textures.minecraft.net/texture/c26b8ce284798954f85bce7f34226ad54d5fc57b55b92161f7e3ff498a3f9890";
    private static ItemStack deleteHead = HeadUtils.getHead(deleteUrl, "backspace");

    private Inventory inventory;
    private InventoryInterface backInv;

    private HashMap<Integer, Runnable> letterCallbacks = new HashMap<>();

    public void setBackInv(InventoryInterface backInv) {
        this.backInv = backInv;
    }

    @Override
    public void open(Player player) {
        inventory = Bukkit.createInventory(null, 54, "Start to type!");
        PluginListener.receivers.add(this);
        player.openInventory(inventory);
        inventory.setItem(0, DisplayBuilder.build(Material.RED_STAINED_GLASS_PANE).withTitle("Back").build());
        for (int i = 1; i < 9; i++) inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        inventory.setItem(17, deleteHead);

        int slotIdx = 8;
        for (int i = 0; i < letterHeads.length; i++) {
            ItemStack stack = letterHeads[i];

            slotIdx++;
            if (slotIdx % 9 == 8) {
                slotIdx++;
            }

            inventory.setItem(slotIdx, stack);

            int finalI = i;
            letterCallbacks.put(slotIdx, () -> {
                String c = String.valueOf(letters.charAt(finalI));
                sendText(c, player);
            });
        }

        inventory.setItem(38, spaceHead);
        inventory.setItem(39, spaceHead);
        inventory.setItem(40, spaceHead);
        inventory.setItem(41, spaceHead);
    }

    @Override
    public void textChange(String newText, Player player) {
        player.getOpenInventory().setTitle(newText);

    }

    @Override
    public void acceptEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        int slot = e.getSlot();
        if (slot == 0) if (backInv != null) {
            backInv.open((Player) e.getWhoClicked());
            return;
        }
        if (slot >= 38 && slot <= 41) sendText(" ", (Player) e.getWhoClicked());
        if (slot == 17) {
            removeText(1, (Player) e.getWhoClicked());
        }

        if (letterCallbacks.containsKey(slot)) {
            Runnable r = letterCallbacks.get(slot);
            r.run();
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void close(InventoryCloseEvent e) {

    }
}
