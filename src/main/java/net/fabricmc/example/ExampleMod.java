package net.fabricmc.example;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.client.ClientConnection;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ModInitializer {


	public static final Item RUBY = new Item(new Item.Settings().group(ItemGroup.MATERIALS));



	private static KeyBinding keyBinding;

	public static String ip = "";
	public static int port = 25565;

	@Environment(EnvType.CLIENT)
	static void clientTick(){
		if(!ip.equalsIgnoreCase("")){
			MinecraftClient.getInstance().openScreen(new ConnectScreen(new TitleScreen(), MinecraftClient.getInstance(), ip, port));
			ip="";
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void onInitialize() {

		ClientTickCallback.EVENT.register(minecraftClient->clientTick());

		MinecraftClient mc = MinecraftClient.getInstance();

		ClientConnection.connectClient("localhost", 44335);

		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"TestClientShortcut", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_V, // The keycode of the key
				"TestClient" // The translation key of the keybinding's category.
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (keyBinding.wasPressed()) {
				//client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
				//mc.player.sendChatMessage("#sel");
				//mc.openScreen(new ConnectScreen(new TitleScreen(), mc, "localhost", 25565));
				//MinecraftClient.getInstance().player.sendMessage(new LiteralText("Test Message!"), false);
				//KeyBinding.setKeyPressed(InputUtil.fromTranslationKey("key.keyboard.w"), true);
				//try {Thread.currentThread().sleep(5000);} catch (InterruptedException ex) {ex.printStackTrace();}
				//KeyBinding.setKeyPressed(InputUtil.fromTranslationKey("key.keyboard.w"), false);
				BlockState standOn = MinecraftClient.getInstance().player.getEntityWorld().getBlockState(new BlockPos(mc.player.getBlockPos().getX(), mc.player.getBlockPos().getY()-1, mc.player.getBlockPos().getZ()));
				standOn.getMaterial();
				standOn.getBlock().toString();

				int blockID = Block.getRawIdFromState(standOn);
				MinecraftClient.getInstance().player.sendMessage(new LiteralText("ID: "+Block.getRawIdFromState(standOn)), false);


			}
		});

		Registry.register(Registry.ITEM, new Identifier("testmod", "ruby"), RUBY);


		System.out.println("Starting Mod...");
	}
}
