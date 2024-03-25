package com.tile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ai.AINode;
import com.blockList.Castle;
import com.blockList.House;
import com.blockList.SolidBlock;
import com.blockList.Tower;
import com.blockList.Tree;
import com.id.BlockType;
import com.id.EntityClass;
import com.id.EntityType;
import com.id.ID;
import com.main.Game;
import com.monsters.GoblinBarrel;
import com.monsters.GoblinTNTField;
import com.monsters.GoblinTorch;
import com.obj.GameObject;
import com.quadTree.Point;
import com.quadTree.Quad;
import com.quadTree.QuadNode;

public class ObjectManager {
    private int WIDTHMAP, HEIGHTMAP;
    public int[][] blocktile_layer1 = new int[0][0];
    public int[][] blocktile_layer2 = new int[0][0];
    int tile_w = 64;
	int tile_h = 64;
	int pixels = 64;
    Game game;

    public List<GameObject> allGameObjects = new ArrayList<>();
    public ObjectManager(Game game){
        this.game = game;
        
    }

    public void loadBlock(int[][] arrays, TileMap[] layer1){
        System.out.println("Load Object!!");
        int col = 0;
        int row = 0;

        while(col<WIDTHMAP && row<HEIGHTMAP) {
			while(col<WIDTHMAP) {
				int tile = arrays[col][row];

                if(tile > 0){
                    // game.handler.objects.add();
                }
				
                col++;
			}
			if(col==WIDTHMAP) {
				col=0;
				row++;
			}
		}
    }
    public int[][] TMXFileReaderBlock(String path, String layer, int[][] arrays){
        String keyword = layer;  // Ganti dengan kata kunci yang ingin dicari
		InputStream in = getClass().getResourceAsStream(path);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            boolean keywordFound = false;

            while ((line = br.readLine()) != null) {
                if (line.contains(keyword)) {
					WIDTHMAP =  extractValueInt(line, "width");
					HEIGHTMAP = extractValueInt(line, "height");
					arrays = new int[WIDTHMAP][HEIGHTMAP];
                    keywordFound = true;
                    break;
                }
            }
			br.readLine();
            if (keywordFound) {
                // Kata kunci ditemukan, lanjutkan membaca 100 baris ke bawah
                int col=0;
				int row=0;

				while(col<WIDTHMAP && row<HEIGHTMAP) {
					
					String a = br.readLine();
					while(col<WIDTHMAP) {
						String[] num = a.split(",");
						
						int number = Integer.parseInt(num[col]);
						
						arrays[col][row] = number;
						col++;
					}
					if(col==WIDTHMAP) {
						col=0;
						row++;
					}
				}
            } else {
                System.out.println("Kata kunci tidak ditemukan dalam file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrays;
	}

    public void TMXFileReaderObject(String path, String layer, Quad qt, List<GameObject> objects, AINode[][] gameObject){
        System.out.println("Read Object!!");
        String keyword = layer;  // Ganti dengan kata kunci yang ingin dicari
		InputStream in = getClass().getResourceAsStream(path);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            boolean keywordFound = false;

            while ((line = br.readLine()) != null) {
                if (line.contains(keyword)) {
                    keywordFound = true;
                    break;
                }
            }
            if (keywordFound) {
				while((line = br.readLine()) != null && line.contains("name")) {
					if(extractValueStr(line, "name").contains("tree") ){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        int h =  extractValueInt(line, "height");
                        Tree object = new Tree(x, y-h, ID.Block, BlockType.DestroyAble, game);
                        qt.insert(new QuadNode(new Point(x, y-h), object), objects, gameObject);
                        allGameObjects.add(object);
                    }else if(extractValueStr(line, "name").contains("enemy")){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        String enClass = extractValueStr(line, "type");
                        if(enClass.contains("goblinTNT")){
                            GoblinTNTField object = new GoblinTNTField(x, y, ID.Entity , EntityType.Monster, EntityClass.Goblins, game);
                            qt.insert(new QuadNode(new Point(x, y), object),objects, gameObject);
                            allGameObjects.add(object);
                        }else if(enClass.contains("goblinBarrel")) {
                            GoblinBarrel object = new GoblinBarrel(x, y, ID.Entity , EntityType.Monster, EntityClass.Goblins, game);
                            qt.insert(new QuadNode(new Point(x, y), object),objects, gameObject);
                            allGameObjects.add(object);
                        }else if(enClass.contains("goblinTorch")) {
                            GoblinTorch object = new GoblinTorch(x, y, ID.Entity , EntityType.Monster, EntityClass.Goblins, game);
                            qt.insert(new QuadNode(new Point(x, y), object),objects, gameObject);
                            allGameObjects.add(object);
                        }
                        line = br.readLine();
                        line = br.readLine();
                    }else if(extractValueStr(line, "name").contains("player")){
                        // int x = extractValueInt(line, "x");
                        // int y = extractValueInt(line, "y");
                        //qt.insert(new QuadNode(new Point(x, y), new Archer(x, y, ID.Entity , EntityType.Player, EntityClass.Archer, game)),objects, gameObject);
                        line = br.readLine();
                        line = br.readLine();
                    }else if(extractValueStr(line, "name").contains("solid")){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        int h =  extractValueInt(line, "height");
                        int w =  extractValueInt(line, "width");
                        qt.insert(new QuadNode(new Point(x, y-h), new SolidBlock(x, y, ID.Block, BlockType.SolidBlock, game, w, h)),objects, gameObject);
                    }else if(extractValueStr(line, "name").contains("blueTower") ){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        int h =  extractValueInt(line, "height");
                        Tower object = new Tower(x, y-h, ID.Block, BlockType.unDestroyAble, game);
                        qt.insert(new QuadNode(new Point(x, y-h), object),objects, gameObject);
                        allGameObjects.add(object);
                    }else if(extractValueStr(line, "name").contains("blueHouse") ){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        int h =  extractValueInt(line, "height");
                        House object = new House(x, y-h, ID.Block, BlockType.unDestroyAble, game);
                        qt.insert(new QuadNode(new Point(x, y-h), object),objects, gameObject);
                        allGameObjects.add(object);
                    }else if(extractValueStr(line, "name").contains("blueCastle") ){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        int h =  extractValueInt(line, "height");
                        Castle object = new Castle(x, y-h, ID.Block, BlockType.unDestroyAble, game);
                        qt.insert(new QuadNode(new Point(x, y-h), object),objects, gameObject);
                        allGameObjects.add(object);
                    }
				}
                
            } else {
                System.out.println("Kata kunci tidak ditemukan dalam file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    private static int extractValueInt(String input, String attribute) {
        // Temukan posisi awal atribut
        int start = input.indexOf(attribute + "=\"");
        if (start == -1) {
            // Atribut tidak ditemukan
            return -1; // atau throw exception, tergantung kebutuhan Anda
        }

        // Hitung posisi akhir atribut
        int end = input.indexOf("\"", start + attribute.length() + 2);
        if (end == -1) {
            // Tanda kutip penutup tidak ditemukan
            return -1; // atau throw exception, tergantung kebutuhan Anda
        }

        // Ekstrak nilai atribut sebagai string
        String value = input.substring(start + attribute.length() + 2, end);
        // Konversi nilai string menjadi integer
        try {
            return (int) Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Penanganan jika konversi gagal
            return -1; // atau throw exception, tergantung kebutuhan Anda
        }
    }
    private static String extractValueStr(String input, String attribute) {
        // Temukan posisi awal atribut
        int start = input.indexOf(attribute + "=\"");
        if (start == -1) {
            // Atribut tidak ditemukan
            return null; // atau throw exception, tergantung kebutuhan Anda
        }

        // Hitung posisi akhir atribut
        int end = input.indexOf("\"", start + attribute.length() + 2);
        if (end == -1) {
            // Tanda kutip penutup tidak ditemukan
            return null; // atau throw exception, tergantung kebutuhan Anda
        }

        // Ekstrak nilai atribut sebagai string
        String value = input.substring(start + attribute.length() + 2, end);

        // Konversi nilai string menjadi integer
        try {
            return value;
        } catch (NumberFormatException e) {
            // Penanganan jika konversi gagal
            e.printStackTrace();
            return null; // atau throw exception, tergantung kebutuhan Anda
        }
    }
}
