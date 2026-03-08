// Description: Java 25 interface for a TopDomain history object

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

/**
 *	ICFIntTopDomainH provides access to history records matching the CFIntTopDomain object change history.
 */
public interface ICFIntTopDomainH
{
	public int getClassCode();

	public CFLibDbKeyHash256 getCreatedByUserId();
	public void setCreatedByUserId( CFLibDbKeyHash256 value );
	public LocalDateTime getCreatedAt();
	public void setCreatedAt( LocalDateTime value );
	public CFLibDbKeyHash256 getUpdatedByUserId();
	public void setUpdatedByUserId( CFLibDbKeyHash256 value );
	public LocalDateTime getUpdatedAt();
	public void setUpdatedAt( LocalDateTime value );

	public ICFIntTopDomainHPKey getPKey();
	public void setPKey( ICFIntTopDomainHPKey pkey );
	public CFLibDbKeyHash256 getAuditClusterId();
	public void setAuditClusterId(CFLibDbKeyHash256 auditClusterId);
	public LocalDateTime getAuditStamp();
	public void setAuditStamp(LocalDateTime auditStamp);
	public short getAuditActionId();
	public void setAuditActionId(short auditActionId);
	public int getRequiredRevision();
	public void setRequiredRevision(int revision);
	public CFLibDbKeyHash256 getAuditSessionId();
	public void setAuditSessionId(CFLibDbKeyHash256 auditSessionId);

	public CFLibDbKeyHash256 getRequiredId();
	public void setRequiredId( CFLibDbKeyHash256 requiredId );

	public CFLibDbKeyHash256 getRequiredTenantId();
	public void setRequiredTenantId( CFLibDbKeyHash256 value );
	public CFLibDbKeyHash256 getRequiredTldId();
	public void setRequiredTldId( CFLibDbKeyHash256 value );
	public String getRequiredName();
	public void setRequiredName( String value );
	public String getOptionalDescription();
	public void setOptionalDescription( String value );
	@Override
	public boolean equals( Object obj );

	@Override
	public int hashCode();

	//@Override
	public int compareTo( Object obj );

	public void set( ICFIntTopDomain src );
	public void set( ICFIntTopDomainH src );
	public void setTopDomain( ICFIntTopDomain src );
	public void setTopDomain( ICFIntTopDomainH src );
	public String getXmlAttrFragment();

	@Override
	public String toString();
}
