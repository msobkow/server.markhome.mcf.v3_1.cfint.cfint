// Description: Java 25 edit object instance implementation for CFInt SecSession.

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

package server.markhome.mcf.v3_1.cfint.cfintobj;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public class CFIntSecSessionEditObj
	implements ICFIntSecSessionEditObj
{
	protected ICFSecSecSessionObj orig;
	protected ICFSecSecSession rec;

	public CFIntSecSessionEditObj( ICFSecSecSessionObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSecSession origRec = orig.getRec();
		rec.set( origRec );
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)orig.getSchema()).getSecSessionTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SecSession" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( null );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredSecSessionId();
		if (val != null) {
			objName = val.toString();
		}
		else {
			objName = "";
		}
		return( objName );
	}

	@Override
	public ICFLibAnyObj getObjQualifier( Class qualifyingClass ) {
		ICFLibAnyObj container = this;
		if( qualifyingClass != null ) {
			while( container != null ) {
				if( container instanceof ICFIntClusterObj ) {
					break;
				}
				else if( container instanceof ICFIntTenantObj ) {
					break;
				}
				else if( qualifyingClass.isInstance( container ) ) {
					break;
				}
				container = container.getObjScope();
			}
		}
		else {
			while( container != null ) {
				if( container instanceof ICFIntClusterObj ) {
					break;
				}
				else if( container instanceof ICFIntTenantObj ) {
					break;
				}
				container = container.getObjScope();
			}
		}
		return( container );
	}

	@Override
	public ICFLibAnyObj getNamedObject( Class qualifyingClass, String objName ) {
		ICFLibAnyObj topContainer = getObjQualifier( qualifyingClass );
		if( topContainer == null ) {
			return( null );
		}
		ICFLibAnyObj namedObject = topContainer.getNamedObject( objName );
		return( namedObject );
	}

	@Override
	public ICFLibAnyObj getNamedObject( String objName ) {
		String nextName;
		String remainingName;
		ICFLibAnyObj subObj = null;
		ICFLibAnyObj retObj;
		int nextDot = objName.indexOf( '.' );
		if( nextDot >= 0 ) {
			nextName = objName.substring( 0, nextDot );
			remainingName = objName.substring( nextDot + 1 );
		}
		else {
			nextName = objName;
			remainingName = null;
		}
		if( remainingName == null ) {
			retObj = subObj;
		}
		else if( subObj == null ) {
			retObj = null;
		}
		else {
			retObj = subObj.getNamedObject( remainingName );
		}
		return( retObj );
	}

	@Override
	public String getObjQualifiedName() {
		String qualName = getObjName();
		ICFLibAnyObj container = getObjScope();
		String containerName;
		while( container != null ) {
			if( container instanceof ICFSecClusterObj ) {
				container = null;
			}
			else if( container instanceof ICFSecTenantObj ) {
				container = null;
			}
			else {
				containerName = container.getObjName();
				qualName = containerName + "." + qualName;
				container = container.getObjScope();
			}
		}
		return( qualName );
	}

	@Override
	public String getObjFullName() {
		String fullName = getObjName();
		ICFLibAnyObj container = getObjScope();
		String containerName;
		while( container != null ) {
			if( container instanceof ICFSecClusterObj ) {
				container = null;
			}
			else if( container instanceof ICFSecTenantObj ) {
				container = null;
			}
			else {
				containerName = container.getObjName();
				fullName = containerName + "." + fullName;
				container = container.getObjScope();
			}
		}
		return( fullName );
	}

	@Override
	public ICFSecSecSessionObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSecSessionObj retobj = getSchema().getSecSessionTableObj().realiseSecSession( (ICFIntSecSessionObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSecSession().forget();
	}

	@Override
	public ICFSecSecSessionObj read() {
		ICFSecSecSessionObj retval = getOrigAsSecSession().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecSessionObj read( boolean forceRead ) {
		ICFSecSecSessionObj retval = getOrigAsSecSession().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecSessionObj create() {
		copyRecToOrig();
		ICFSecSecSessionObj retobj = ((ICFIntSchemaObj)getOrigAsSecSession().getSchema()).getSecSessionTableObj().createSecSession( getOrigAsSecSession() );
		if( retobj == getOrigAsSecSession() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSecSessionEditObj update() {
		getSchema().getSecSessionTableObj().updateSecSession( (ICFSecSecSessionObj)this );
		return( null );
	}

	@Override
	public CFSecSecSessionEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSecSessionTableObj().deleteSecSession( getOrigAsSecSession() );
		return( null );
	}

	@Override
	public ICFSecSecSessionTableObj getSecSessionTable() {
		return( orig.getSchema().getSecSessionTableObj() );
	}

	@Override
	public ICFSecSecSessionEditObj getEdit() {
		return( (ICFSecSecSessionEditObj)this );
	}

	@Override
	public ICFSecSecSessionEditObj getEditAsSecSession() {
		return( (ICFSecSecSessionEditObj)this );
	}

	@Override
	public ICFSecSecSessionEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSecSessionObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSecSessionObj getOrigAsSecSession() {
		return( (ICFSecSecSessionObj)orig );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( orig.getSchema() );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		orig.setSchema(value);
	}

	@Override
	public ICFSecSecSession getRec() {
		if( rec == null ) {
			rec = getOrigAsSecSession().getSchema().getCFSecBackingStore().getFactorySecSession().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecSession value ) {
		if( rec != value ) {
			rec = value;
		}
	}

	@Override
	public ICFSecSecSession getSecSessionRec() {
		return( (ICFSecSecSession)getRec() );
	}

	@Override
	public CFLibDbKeyHash256 getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( CFLibDbKeyHash256 value ) {
		orig.setPKey( value );
		copyPKeyToRec();
	}

	@Override
	public boolean getIsNew() {
		return( orig.getIsNew() );
	}

	@Override
	public void setIsNew( boolean value ) {
		orig.setIsNew( value );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecSessionId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredSecSessionId(CFLibDbKeyHash256 secSessionId) {
		if (getPKey() != secSessionId) {
			setPKey(secSessionId);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( getSecSessionRec().getRequiredSecUserId() );
	}

	@Override
	public void setRequiredSecUserId( CFLibDbKeyHash256 value ) {
		if( getSecSessionRec().getRequiredSecUserId() != value ) {
			getSecSessionRec().setRequiredSecUserId( value );
		}
	}

	@Override
	public String getOptionalSecDevName() {
		return( getSecSessionRec().getOptionalSecDevName() );
	}

	@Override
	public void setOptionalSecDevName( String value ) {
		if( getSecSessionRec().getOptionalSecDevName() != value ) {
			getSecSessionRec().setOptionalSecDevName( value );
		}
	}

	@Override
	public LocalDateTime getRequiredStart() {
		return( getSecSessionRec().getRequiredStart() );
	}

	@Override
	public void setRequiredStart( LocalDateTime value ) {
		if( getSecSessionRec().getRequiredStart() != value ) {
			getSecSessionRec().setRequiredStart( value );
		}
	}

	@Override
	public LocalDateTime getOptionalFinish() {
		return( getSecSessionRec().getOptionalFinish() );
	}

	@Override
	public void setOptionalFinish( LocalDateTime value ) {
		if( getSecSessionRec().getOptionalFinish() != value ) {
			getSecSessionRec().setOptionalFinish( value );
		}
	}

	@Override
	public CFLibDbKeyHash256 getOptionalSecProxyId() {
		return( getSecSessionRec().getOptionalSecProxyId() );
	}

	@Override
	public void setOptionalSecProxyId( CFLibDbKeyHash256 value ) {
		if( getSecSessionRec().getOptionalSecProxyId() != value ) {
			getSecSessionRec().setOptionalSecProxyId( value );
		}
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				rec.setPKey(getPKey());
			}
		}
	}

	@Override
	public void copyRecToPKey() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				setPKey(rec.getPKey());
			}
		}
	}

	@Override
	public void copyRecToOrig() {
		ICFSecSecSession origRec = getOrigAsSecSession().getSecSessionRec();
		ICFSecSecSession myRec = getSecSessionRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSecSession origRec = getOrigAsSecSession().getSecSessionRec();
		ICFSecSecSession myRec = getSecSessionRec();
		myRec.set( origRec );
	}
}
