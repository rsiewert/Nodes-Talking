package com.topaz.nodes;
import java.util.*;

public class Server extends Node {

	public enum ServiceTypes {REGISTRATION, LOGGING}
	
	ArrayList<ServiceTypes> services;
	
	private Server() {};
}
