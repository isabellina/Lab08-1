package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private Graph<Airport, DefaultWeightedEdge> grafo;
	private Map<Integer, Airport> mappaAereoporti;
	private ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
	
	
	public Model() {
		
		this.mappaAereoporti = new HashMap<Integer,Airport>();
	}
	
	
	public void creaGrafo(double distanza) {
		
	
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllAirports(mappaAereoporti);
		
		Graphs.addAllVertices(this.grafo, mappaAereoporti.values());
		
		
		for(Arco a : dao.getArchi()) {
			if(a.getMedia()> distanza) {
				Graphs.addEdge(this.grafo, mappaAereoporti.get(a.getA1()), mappaAereoporti.get(a.getA2()), a.getMedia());
				
			}
		}
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> getInfo(double d) {
		List<Arco> info = new LinkedList<Arco>();
		
		for(Arco a : dao.getArchi() ) {
			if(a.getMedia()>d) {
			info.add(a);
			}
		}
		return info;
	}
}
