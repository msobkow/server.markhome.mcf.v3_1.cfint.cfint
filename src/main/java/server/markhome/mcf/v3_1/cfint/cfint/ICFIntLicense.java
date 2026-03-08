// Description: Java 25 interface for a License record implementation

/*
 *	server.markhome.mcf.CFInt
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFInt - Internet Essentials
 *	
 *	This file is part of Mark's Code Fractal CFInt.
 *	
 *	Mark's Code Fractal CFInt is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFInt is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFInt is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFInt.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfint.cfint;

import java.io.Serializable;
import java.math.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
//import server.markhome.mcf.v3_1.cfint.cfint.*;

public interface ICFIntLicense
{
	public static final String S_ID_INIT_VALUE = "$switch HasInitValue yes InitValue default Zero256bits$";
	public static final CFLibDbKeyHash256 ID_INIT_VALUE = CFLibDbKeyHash256.fromHex( S_ID_INIT_VALUE );
	public static final String S_TENANTID_INIT_VALUE = "$switch HasInitValue yes InitValue default Zero256bits$";
	public static final CFLibDbKeyHash256 TENANTID_INIT_VALUE = CFLibDbKeyHash256.fromHex( S_TENANTID_INIT_VALUE );
	public static final String S_TOPDOMAINID_INIT_VALUE = "$switch HasInitValue yes InitValue default Zero256bits$";
	public static final CFLibDbKeyHash256 TOPDOMAINID_INIT_VALUE = CFLibDbKeyHash256.fromHex( S_TOPDOMAINID_INIT_VALUE );
	public static final String NAME_INIT_VALUE = new String( "" );
	public static final String DESCRIPTION_INIT_VALUE = new String( "" );
	public static final String EMBEDDEDTEXT_INIT_VALUE = new String( "" );
	public static final String FULLTEXT_INIT_VALUE = new String( "" );
	public final static int CLASS_CODE = 0xa110;
	public final static String S_CLASS_CODE = "a110";

	public int getClassCode();

	public CFLibDbKeyHash256 getPKey();
	public void setPKey(CFLibDbKeyHash256 requiredId);
	
	public CFLibDbKeyHash256 getRequiredId();
	public void setRequiredId( CFLibDbKeyHash256 value );
	public int getRequiredRevision();
	public void setRequiredRevision( int value );

	public ICFSecTenant getRequiredOwnerTenant();
	public ICFIntTopDomain getRequiredContainerTopDomain();
	public void setRequiredOwnerTenant(ICFSecTenant argObj);
	public void setRequiredOwnerTenant(CFLibDbKeyHash256 argTenantId);
	public void setRequiredContainerTopDomain(ICFIntTopDomain argObj);
	public void setRequiredContainerTopDomain(CFLibDbKeyHash256 argTopDomainId);
	public CFLibDbKeyHash256 getRequiredTenantId();
	public CFLibDbKeyHash256 getRequiredTopDomainId();
	public String getRequiredName();
	public void setRequiredName( String value );
	public String getOptionalDescription();
	public void setOptionalDescription( String value );
	public String getOptionalEmbeddedText();
	public void setOptionalEmbeddedText( String value );
	public String getOptionalFullText();
	public void setOptionalFullText( String value );
	@Override
	public boolean equals( Object obj );
	
	@Override
	public int hashCode();

	//@Override not necessary because interfaces aren't able to implement Comparable, but they can double-team on the requirement
	public int compareTo( Object obj );

	public void set( ICFIntLicense src );
	public void setLicense( ICFIntLicense src );
	public String getXmlAttrFragment();

	@Override
	public String toString();
}
