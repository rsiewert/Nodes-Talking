module sensorPackageHalf() {
difference() {
		//the original cube
		cube([60,40,10],center=true);

		//the diff cube less in x,y by 2 and offset in z giving a 
		//thickness of 2 on all sides
		translate([0,0,2]) {
			cube([58,38,10],center=true);
		}
	}
	translate([0,0,-5]) {
		cylinder(5,r=1,center=false);
	} 
	translate([0,15,-5]) {
		cylinder(5,r=1,center=false);
	} 
	translate([25,0,-5]) {
		cylinder(5,r=1,center=false);
	} 
	translate([25,15,-5]) {
		cylinder(5,r=1,center=false);
	} 
}
sensorPackageHalf();
