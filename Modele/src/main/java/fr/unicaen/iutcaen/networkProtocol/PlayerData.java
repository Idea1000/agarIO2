package fr.unicaen.iutcaen.networkProtocol;

import java.util.ArrayList;
import java.util.List;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.factories.FactoryPlayer;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;

public class PlayerData extends ProtocolData {
	
	private List<EntityData> cellsPackData; 
	private int id; 
	
	public PlayerData(Player player) {
		cellsPackData = new ArrayList<EntityData>(); 
		ObservableList<Cell> cells = player.getCells().getAllCells(); 
		id = player.getId(); 
		
		for(Cell cell : cells) {
			cellsPackData.add(new EntityData(cell)); 
		}
		
	}
	
	public Player convertToPlayer() {
		Player player = FactoryPlayer.fabriquePlayer();
		
		player.setId(id);	
		ObservableList<Cell> cells = player.getCells().getAllCells(); 
		
		for( EntityData data : cellsPackData) {
			cells.add((Cell) data.convertToEntity());
		}
		
		return player;
	}
	
}
