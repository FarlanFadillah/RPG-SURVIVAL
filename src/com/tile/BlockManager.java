package com.tile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.blockList.SolidBlock;
import com.blockList.Tree;
import com.id.BlockType;
import com.id.ID;
import com.main.Game;
import com.obj.GameObject;

public class BlockManager {
    private int WIDTHMAP, HEIGHTMAP;
    public int[][] blocktile_layer1 = new int[0][0];
    public int[][] blocktile_layer2 = new int[0][0];
    int tile_w = 64;
	int tile_h = 64;
	int pixels = 64;
    Game game;
    public BlockManager(Game game){
        this.game = game;
        
    }

    public void loadBlock(int[][] arrays, TileMap[] layer1, ArrayList<GameObject> objects){
        System.out.println("Load Object!!");
        int col = 0;
        int row = 0;

        while(col<WIDTHMAP && row<HEIGHTMAP) {
			while(col<WIDTHMAP) {
				int tile = arrays[col][row];

                if(tile > 0){
                    objects.add(new SolidBlock(col*pixels, row*pixels, ID.Block, BlockType.unDestroyAble, game, layer1[tile].image));
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

    public void TMXFileReaderObject(String path, String layer){
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
                        game.handler.objects.add(new Tree(x, y-h, ID.Block, BlockType.DestroyAble, game));
                    }else if(extractValueStr(line, "name").contains("player")){
                        int x = extractValueInt(line, "x");
                        int y = extractValueInt(line, "y");
                        int h =  extractValueInt(line, "height");
                        game.handler.archer.arah = "bawah";
                        game.handler.archer.setX(x);
                        game.handler.archer.setY(y-h);
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
