package com.autohome.adrd.algo.click_model.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.apache.hadoop.io.Writable;

import com.autohome.adrd.algo.click_model.utility.MyPair;



public class Sample extends Features implements Writable {
	private double label;

	public Sample() {
		super();
		label = 0.0;
	}
	
	
	public Object clone() {
		Sample o = (Sample) super.clone();
		o.setLabel(label);
		return o;
	}
	

	
	public double getLabel() {
		return label;
	}
	
	public void setLabel(double _label) {
		label = _label;
	}
	
	public String toString() {
		return label + "\t" + super.toString();
	}
	
	public static Sample fromString(String str) {
		Sample sample = new Sample();
		Scanner in = new Scanner(str);
		sample.setLabel(in.nextDouble());
		
		int sz = in.nextInt();
		for(int i = 0; i < sz; i++) {
			sample.setFeature(in.next());
		}
		sz = in.nextInt();
		String feature;
		double value;
		for(int i = 0; i < sz; i++) {
			feature = in.next();
			value = in.nextDouble();
			sample.setFeature(feature, value);
		}
		in.close();
		
		return sample;
		
	}


	@Override
	public void readFields(DataInput in) throws IOException {
		//id_fea_vec=new Vector<Integer>();
		//float_fea_vec=new Vector<MyPair<Integer,Double>>();
		HashSet<String> id_fea_vec = new HashSet<String>();
		HashMap<String, Double> float_fea_vec = new HashMap<String, Double>();
		this.label=in.readDouble();//labelize_features
		int id_size=in.readInt();
		for(int i=0; i< id_size; i++){//id features
			id_fea_vec.add(in.readUTF());
		}
		
		int float_size=in.readInt();
		for(int i=0; i< float_size; i++){
			float_fea_vec.put(in.readUTF(), in.readDouble());
		}
		
	}


	@Override
	public void write(DataOutput out) throws IOException {
		//write double label
		out.writeDouble(this.getLabel());
		//write id_fea
		out.writeInt(this.getIdFeatures().size());
		for(String fea: this.getIdFeatures()){
			out.writeUTF(fea);
		}
		
		//write float fea
		out.writeInt(this.getFloatFeatures().size());
		for(Map.Entry<String, Double> mp: this.getFloatFeatures().entrySet()){
			out.writeUTF(mp.getKey());
			out.writeDouble(mp.getValue());
		}
		
	}
}
