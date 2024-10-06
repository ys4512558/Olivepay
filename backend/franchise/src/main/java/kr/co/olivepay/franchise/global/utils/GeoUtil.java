package kr.co.olivepay.franchise.global.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeoUtil {

	private static final BigDecimal EARTH_RADIUS_M = new BigDecimal("6371000.0");
	private static final BigDecimal PI = new BigDecimal(Math.PI);

	public static Double metersToLatitude(double meters) {
		BigDecimal metersBD = new BigDecimal(Double.toString(meters));
		BigDecimal result = metersBD.divide(EARTH_RADIUS_M, 15, RoundingMode.HALF_UP)
									.multiply(new BigDecimal("180"))
									.divide(PI, 15, RoundingMode.HALF_UP);
		return result.doubleValue();
	}

	public static Double metersToLongitude(double meters, double latitude) {
		BigDecimal metersBD = new BigDecimal(Double.toString(meters));
		BigDecimal latitudeBD = new BigDecimal(Double.toString(latitude));
		BigDecimal latitudeRadians = latitudeBD.multiply(PI).divide(new BigDecimal("180"), 15, RoundingMode.HALF_UP);
		BigDecimal cosLat = new BigDecimal(Math.cos(latitudeRadians.doubleValue()));
		BigDecimal result = metersBD.divide(EARTH_RADIUS_M.multiply(cosLat), 15, RoundingMode.HALF_UP)
									.multiply(new BigDecimal("180"))
									.divide(PI, 15, RoundingMode.HALF_UP);
		return result.doubleValue();
	}

}
