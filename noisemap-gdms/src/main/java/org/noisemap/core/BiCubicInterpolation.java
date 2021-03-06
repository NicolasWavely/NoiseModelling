/**
 * NoiseMap is a scientific computation plugin for OrbisGIS developed in order to
 * evaluate the noise impact on urban mobility plans. This model is
 * based on the French standard method NMPB2008. It includes traffic-to-noise
 * sources evaluation and sound propagation processing.
 *
 * This version is developed at French IRSTV Institute and at IFSTTAR
 * (http://www.ifsttar.fr/) as part of the Eval-PDU project, funded by the
 * French Agence Nationale de la Recherche (ANR) under contract ANR-08-VILL-0005-01.
 *
 * Noisemap is distributed under GPL 3 license. Its reference contact is Judicaël
 * Picaut <judicael.picaut@ifsttar.fr>. It is maintained by Nicolas Fortin
 * as part of the "Atelier SIG" team of the IRSTV Institute <http://www.irstv.fr/>.
 *
 * Copyright (C) 2011 IFSTTAR
 * Copyright (C) 2011-2012 IRSTV (FR CNRS 2488)
 *
 * Noisemap is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Noisemap is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Noisemap. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.noisemap.core;

/**
 * 
 * @author Nicolas Fortin
 * @author Paul Breeuwsma (Enschede, The Netherlands)
 * @see http://www.paulinternet.nl/?page=bicubic
 */
public class BiCubicInterpolation {
	private double a00, a01, a02, a03;
	private double a10, a11, a12, a13;
	private double a20, a21, a22, a23;
	private double a30, a31, a32, a33;

	public void updateCoefficients(double[][] p) {
		a00 = p[1][1];
		a01 = -.5 * p[1][0] + .5 * p[1][2];
		a02 = p[1][0] - 2.5 * p[1][1] + 2 * p[1][2] - .5 * p[1][3];
		a03 = -.5 * p[1][0] + 1.5 * p[1][1] - 1.5 * p[1][2] + .5 * p[1][3];
		a10 = -.5 * p[0][1] + .5 * p[2][1];
		a11 = .25 * p[0][0] - .25 * p[0][2] - .25 * p[2][0] + .25 * p[2][2];
		a12 = -.5 * p[0][0] + 1.25 * p[0][1] - p[0][2] + .25 * p[0][3] + .5
				* p[2][0] - 1.25 * p[2][1] + p[2][2] - .25 * p[2][3];
		a13 = .25 * p[0][0] - .75 * p[0][1] + .75 * p[0][2] - .25 * p[0][3]
				- .25 * p[2][0] + .75 * p[2][1] - .75 * p[2][2] + .25 * p[2][3];
		a20 = p[0][1] - 2.5 * p[1][1] + 2 * p[2][1] - .5 * p[3][1];
		a21 = -.5 * p[0][0] + .5 * p[0][2] + 1.25 * p[1][0] - 1.25 * p[1][2]
				- p[2][0] + p[2][2] + .25 * p[3][0] - .25 * p[3][2];
		a22 = p[0][0] - 2.5 * p[0][1] + 2 * p[0][2] - .5 * p[0][3] - 2.5
				* p[1][0] + 6.25 * p[1][1] - 5 * p[1][2] + 1.25 * p[1][3] + 2
				* p[2][0] - 5 * p[2][1] + 4 * p[2][2] - p[2][3] - .5 * p[3][0]
				+ 1.25 * p[3][1] - p[3][2] + .25 * p[3][3];
		a23 = -.5 * p[0][0] + 1.5 * p[0][1] - 1.5 * p[0][2] + .5 * p[0][3]
				+ 1.25 * p[1][0] - 3.75 * p[1][1] + 3.75 * p[1][2] - 1.25
				* p[1][3] - p[2][0] + 3 * p[2][1] - 3 * p[2][2] + p[2][3] + .25
				* p[3][0] - .75 * p[3][1] + .75 * p[3][2] - .25 * p[3][3];
		a30 = -.5 * p[0][1] + 1.5 * p[1][1] - 1.5 * p[2][1] + .5 * p[3][1];
		a31 = .25 * p[0][0] - .25 * p[0][2] - .75 * p[1][0] + .75 * p[1][2]
				+ .75 * p[2][0] - .75 * p[2][2] - .25 * p[3][0] + .25 * p[3][2];
		a32 = -.5 * p[0][0] + 1.25 * p[0][1] - p[0][2] + .25 * p[0][3] + 1.5
				* p[1][0] - 3.75 * p[1][1] + 3 * p[1][2] - .75 * p[1][3] - 1.5
				* p[2][0] + 3.75 * p[2][1] - 3 * p[2][2] + .75 * p[2][3] + .5
				* p[3][0] - 1.25 * p[3][1] + p[3][2] - .25 * p[3][3];
		a33 = .25 * p[0][0] - .75 * p[0][1] + .75 * p[0][2] - .25 * p[0][3]
				- .75 * p[1][0] + 2.25 * p[1][1] - 2.25 * p[1][2] + .75
				* p[1][3] + .75 * p[2][0] - 2.25 * p[2][1] + 2.25 * p[2][2]
				- .75 * p[2][3] - .25 * p[3][0] + .75 * p[3][1] - .75 * p[3][2]
				+ .25 * p[3][3];
	}
	public static double getValue (double[] p, double x) {
		return p[1] + 0.5 * x*(p[2] - p[0] + x*(2.0*p[0] - 5.0*p[1] + 4.0*p[2] - p[3] + x*(3.0*(p[1] - p[2]) + p[3] - p[0])));
	}
	public double getValue(double x, double y) {
		double x2 = x * x;
		double x3 = x2 * x;
		double y2 = y * y;
		double y3 = y2 * y;

		return (a00 + a01 * y + a02 * y2 + a03 * y3)
				+ (a10 + a11 * y + a12 * y2 + a13 * y3) * x
				+ (a20 + a21 * y + a22 * y2 + a23 * y3) * x2
				+ (a30 + a31 * y + a32 * y2 + a33 * y3) * x3;
	}
}