// Description: Java 25 edit object instance implementation for CFInt URLProtocol.

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
import server.markhome.mcf.v3_1.cfint.cfint.*;

public class CFIntURLProtocolEditObj
	implements ICFIntURLProtocolEditObj
{
	protected ICFIntURLProtocolObj orig;
	protected ICFIntURLProtocol rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;

	public CFIntURLProtocolEditObj( ICFIntURLProtocolObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFIntURLProtocol origRec = orig.getRec();
		rec.set( origRec );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFIntURLProtocol rec = getRec();
			createdBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getCreatedByUserId() );
		}
		return( createdBy );
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return( getRec().getCreatedAt() );
	}

	@Override
	public ICFSecSecUserObj getUpdatedBy() {
		if( updatedBy == null ) {
			ICFIntURLProtocol rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public void setCreatedBy( ICFSecSecUserObj value ) {
		createdBy = value;
		if( value != null ) {
			getRec().setCreatedByUserId( value.getRequiredSecUserId() );
		}
	}

	@Override
	public void setCreatedAt( LocalDateTime value ) {
		getRec().setCreatedAt( value );
	}

	@Override
	public void setUpdatedBy( ICFSecSecUserObj value ) {
		updatedBy = value;
		if( value != null ) {
			getRec().setUpdatedByUserId( value.getRequiredSecUserId() );
		}
	}

	@Override
	public void setUpdatedAt( LocalDateTime value ) {
		getRec().setUpdatedAt( value );
	}

	@Override
	public int getClassCode() {
		return( ((ICFIntSchemaObj)orig.getSchema()).getURLProtocolTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "URLProtocol" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( null );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredName();
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
	public ICFIntURLProtocolObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFIntURLProtocolObj retobj = getSchema().getURLProtocolTableObj().realiseURLProtocol( (ICFIntURLProtocolObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsURLProtocol().forget();
	}

	@Override
	public ICFIntURLProtocolObj read() {
		ICFIntURLProtocolObj retval = getOrigAsURLProtocol().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntURLProtocolObj read( boolean forceRead ) {
		ICFIntURLProtocolObj retval = getOrigAsURLProtocol().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntURLProtocolObj create() {
		copyRecToOrig();
		ICFIntURLProtocolObj retobj = ((ICFIntSchemaObj)getOrigAsURLProtocol().getSchema()).getURLProtocolTableObj().createURLProtocol( getOrigAsURLProtocol() );
		if( retobj == getOrigAsURLProtocol() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFIntURLProtocolEditObj update() {
		getSchema().getURLProtocolTableObj().updateURLProtocol( (ICFIntURLProtocolObj)this );
		return( null );
	}

	@Override
	public CFIntURLProtocolEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getURLProtocolTableObj().deleteURLProtocol( getOrigAsURLProtocol() );
		return( null );
	}

	@Override
	public ICFIntURLProtocolTableObj getURLProtocolTable() {
		return( orig.getSchema().getURLProtocolTableObj() );
	}

	@Override
	public ICFIntURLProtocolEditObj getEdit() {
		return( (ICFIntURLProtocolEditObj)this );
	}

	@Override
	public ICFIntURLProtocolEditObj getEditAsURLProtocol() {
		return( (ICFIntURLProtocolEditObj)this );
	}

	@Override
	public ICFIntURLProtocolEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFIntURLProtocolObj getOrig() {
		return( orig );
	}

	@Override
	public ICFIntURLProtocolObj getOrigAsURLProtocol() {
		return( (ICFIntURLProtocolObj)orig );
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( orig.getSchema() );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
		orig.setSchema(value);
	}

	@Override
	public ICFIntURLProtocol getRec() {
		if( rec == null ) {
			rec = getOrigAsURLProtocol().getSchema().getCFIntBackingStore().getFactoryURLProtocol().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntURLProtocol value ) {
		if( rec != value ) {
			rec = value;
		}
	}

	@Override
	public ICFIntURLProtocol getURLProtocolRec() {
		return( (ICFIntURLProtocol)getRec() );
	}

	@Override
	public Integer getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( Integer value ) {
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
	public int getRequiredURLProtocolId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredURLProtocolId(int uRLProtocolId) {
		if (getPKey() != uRLProtocolId) {
			setPKey(uRLProtocolId);
		}
	}

	@Override
	public String getRequiredName() {
		return( getURLProtocolRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getURLProtocolRec().getRequiredName() != value ) {
			getURLProtocolRec().setRequiredName( value );
		}
	}

	@Override
	public String getRequiredDescription() {
		return( getURLProtocolRec().getRequiredDescription() );
	}

	@Override
	public void setRequiredDescription( String value ) {
		if( getURLProtocolRec().getRequiredDescription() != value ) {
			getURLProtocolRec().setRequiredDescription( value );
		}
	}

	@Override
	public boolean getRequiredIsSecure() {
		return( getURLProtocolRec().getRequiredIsSecure() );
	}

	@Override
	public void setRequiredIsSecure( boolean value ) {
		if( getURLProtocolRec().getRequiredIsSecure() != value ) {
			getURLProtocolRec().setRequiredIsSecure( value );
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
		ICFIntURLProtocol origRec = getOrigAsURLProtocol().getURLProtocolRec();
		ICFIntURLProtocol myRec = getURLProtocolRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFIntURLProtocol origRec = getOrigAsURLProtocol().getURLProtocolRec();
		ICFIntURLProtocol myRec = getURLProtocolRec();
		myRec.set( origRec );
	}
}
