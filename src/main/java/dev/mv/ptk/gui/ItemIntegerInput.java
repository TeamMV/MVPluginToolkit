package dev.mv.ptk.gui;

import dev.mv.ptk.gui.border.BorderFrame;
import dev.mv.ptk.style.UiStyle;
import dev.mv.ptk.utils.HeadUtils;
import dev.mv.ptk.utils.State;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemIntegerInput extends Component1x1 {
    private static ItemStack plus1Head = HeadUtils.getHead("http://textures.minecraft.net/texture/177bb66fc73a97cefcb3a4bfdccb12281f44dd326ccd0ff39d47e985bfeff343", "+1", 1);
    private static ItemStack plus5Head = HeadUtils.getHead("http://textures.minecraft.net/texture/177bb66fc73a97cefcb3a4bfdccb12281f44dd326ccd0ff39d47e985bfeff343", "+5", 5);
    private static ItemStack minus1Head = HeadUtils.getHead("http://textures.minecraft.net/texture/14c48a661a524b01427fd1c34ae07076c570a0711f0ab703737501af37231631", "-1", 1);
    private static ItemStack minus5Head = HeadUtils.getHead("http://textures.minecraft.net/texture/14c48a661a524b01427fd1c34ae07076c570a0711f0ab703737501af37231631", "-5", 5);
    private static ItemStack zeroHead = HeadUtils.getHead("http://textures.minecraft.net/texture/bdb01a3077562843090e030ee7b4a8634e6fc2d53e4603a34f28f5f2adc3371d", "zero", 1);
    private static ItemStack mul2Head = HeadUtils.getHead("http://textures.minecraft.net/texture/73ca7acf7e906bc0f0c45ca2dba26f2040e8d05bbf53585d375fdd46f78a2d27", "times 2", 2);
    private static ItemStack div2Head = HeadUtils.getHead("http://textures.minecraft.net/texture/57b1791bdc46d8a5c51729e8982fd439bb40513f64b5babee93294efc1c7", "divide by 2", 2);

    private State<Integer> state;
    private Vec<Listener> listeners;
    private InventoryInterface inventoryInterface;

    private ItemStack display;

    public ItemIntegerInput(ItemStack display,  String title) {
        super(null);
        this.display = display;
        listeners = new Vec<>();
        state = new State<>(0);
        inventoryInterface = (InventoryInterface) new InventoryInterface(title)
                .with(new BorderFrame(new ItemStack(Material.OAK_SIGN))
                        .with(new InsetFrame(1, 1, 1, 1)
                                .with(new HFrame()
                                        .with(new VFrame()
                                                .with(new ItemStateDisplay(new ItemStack(Material.PAPER), state))
                                                .with(new ItemButton(plus1Head).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, v -> v + 1);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                                .with(new ItemButton(plus5Head).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, v -> v + 5);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                                .with(new ItemButton(zeroHead).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, 0);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                                .with(new ItemButton(minus1Head).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, v -> v - 1);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                                .with(new ItemButton(minus5Head).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, v -> v - 5);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                        )
                                        .with(new VFrame()
                                                .with(new ItemButton(DisplayBuilder.build(Material.RED_STAINED_GLASS_PANE).withTitle("back").build())
                                                        .withListener((_0, _1, pl) -> {
                                                            getInterface().open((Player) pl);
                                                            return false;
                                                        })
                                                )
                                                .with(new ItemButton(mul2Head).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, v -> v * 2);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                                .with(new ItemButton(div2Head).withListener((_0, _1, pl) -> {
                                                    state.write((Player) pl, v -> v / 2);
                                                    changeValue(state.read(), (Player) pl);
                                                    return false;
                                                }))
                                        )
                                )
                        )
                );
    }

    public void addListener(Listener listener) {
        listeners.push(listener);
    }

    public int getValue() {
        return state.read();
    }

    public State<Integer> getState() {
        return state;
    }

    public void changeValue(int value, Player player) {
        state.write(player, value);
        listeners.forEach(l -> l.onValueChange(value, player));
    }

    @Override
    public void open(Inventory inventory, UiStyle style) {
        inventory.setItem(slot, display);
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            inventoryInterface.open((Player) e.getWhoClicked());
        }
        return false;
    }

    public interface Listener {
        void onValueChange(int newValue, Player player);
    }
}
