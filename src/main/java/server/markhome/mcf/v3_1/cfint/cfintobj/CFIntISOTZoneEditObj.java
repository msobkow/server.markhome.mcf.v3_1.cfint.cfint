// Description: Java 25 edit object instance implementation for CFInt ISOTZone.

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

public class CFIntISOTZoneEditObj
	implements ICFIntISOTZoneEditObj
{
	protected ICFSecISOTZoneObj orig;
	protected ICFSecISOTZone rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;

	public CFIntISOTZoneEditObj( ICFSecISOTZoneObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecISOTZone origRec = orig.getRec();
		rec.set( origRec );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecISOTZone rec = getRec();
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
			ICFSecISOTZone rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getISOTZoneTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "ISOTZone" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( null );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredTZName();
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
	public ICFSecISOTZoneObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecISOTZoneObj retobj = getSchema().getISOTZoneTableObj().realiseISOTZone( (ICFIntISOTZoneObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsISOTZone().forget();
	}

	@Override
	public ICFSecISOTZoneObj read() {
		ICFSecISOTZoneObj retval = getOrigAsISOTZone().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecISOTZoneObj read( boolean forceRead ) {
		ICFSecISOTZoneObj retval = getOrigAsISOTZone().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecISOTZoneObj create() {
		copyRecToOrig();
		ICFSecISOTZoneObj retobj = ((ICFIntSchemaObj)getOrigAsISOTZone().getSchema()).getISOTZoneTableObj().createISOTZone( getOrigAsISOTZone() );
		if( retobj == getOrigAsISOTZone() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecISOTZoneEditObj update() {
		getSchema().getISOTZoneTableObj().updateISOTZone( (ICFSecISOTZoneObj)this );
		return( null );
	}

	@Override
	public CFSecISOTZoneEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getISOTZoneTableObj().deleteISOTZone( getOrigAsISOTZone() );
		return( null );
	}

	@Override
	public ICFSecISOTZoneTableObj getISOTZoneTable() {
		return( orig.getSchema().getISOTZoneTableObj() );
	}

	@Override
	public ICFSecISOTZoneEditObj getEdit() {
		return( (ICFSecISOTZoneEditObj)this );
	}

	@Override
	public ICFSecISOTZoneEditObj getEditAsISOTZone() {
		return( (ICFSecISOTZoneEditObj)this );
	}

	@Override
	public ICFSecISOTZoneEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecISOTZoneObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecISOTZoneObj getOrigAsISOTZone() {
		return( (ICFSecISOTZoneObj)orig );
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
	public ICFSecISOTZone getRec() {
		if( rec == null ) {
			rec = getOrigAsISOTZone().getSchema().getCFSecBackingStore().getFactoryISOTZone().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecISOTZone value ) {
		if( rec != value ) {
			rec = value;
		}
	}

	@Override
	public ICFSecISOTZone getISOTZoneRec() {
		return( (ICFSecISOTZone)getRec() );
	}

	@Override
	public Short getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( Short value ) {
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
	public short getRequiredISOTZoneId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredISOTZoneId(short iSOTZoneId) {
		if (getPKey() != iSOTZoneId) {
			setPKey(iSOTZoneId);
		}
	}

	@Override
	public String getRequiredIso8601() {
		return( getISOTZoneRec().getRequiredIso8601() );
	}

	@Override
	public void setRequiredIso8601( String value ) {
		if( getISOTZoneRec().getRequiredIso8601() != value ) {
			getISOTZoneRec().setRequiredIso8601( value );
		}
	}

	@Override
	public String getRequiredTZName() {
		return( getISOTZoneRec().getRequiredTZName() );
	}

	@Override
	public void setRequiredTZName( String value ) {
		if( getISOTZoneRec().getRequiredTZName() != value ) {
			getISOTZoneRec().setRequiredTZName( value );
		}
	}

	@Override
	public short getRequiredTZHourOffset() {
		return( getISOTZoneRec().getRequiredTZHourOffset() );
	}

	@Override
	public void setRequiredTZHourOffset( short value ) {
		if( getISOTZoneRec().getRequiredTZHourOffset() != value ) {
			getISOTZoneRec().setRequiredTZHourOffset( value );
		}
	}

	@Override
	public short getRequiredTZMinOffset() {
		return( getISOTZoneRec().getRequiredTZMinOffset() );
	}

	@Override
	public void setRequiredTZMinOffset( short value ) {
		if( getISOTZoneRec().getRequiredTZMinOffset() != value ) {
			getISOTZoneRec().setRequiredTZMinOffset( value );
		}
	}

	@Override
	public String getRequiredDescription() {
		return( getISOTZoneRec().getRequiredDescription() );
	}

	@Override
	public void setRequiredDescription( String value ) {
		if( getISOTZoneRec().getRequiredDescription() != value ) {
			getISOTZoneRec().setRequiredDescription( value );
		}
	}

	@Override
	public boolean getRequiredVisible() {
		return( getISOTZoneRec().getRequiredVisible() );
	}

	@Override
	public void setRequiredVisible( boolean value ) {
		if( getISOTZoneRec().getRequiredVisible() != value ) {
			getISOTZoneRec().setRequiredVisible( value );
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
		ICFSecISOTZone origRec = getOrigAsISOTZone().getISOTZoneRec();
		ICFSecISOTZone myRec = getISOTZoneRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecISOTZone origRec = getOrigAsISOTZone().getISOTZoneRec();
		ICFSecISOTZone myRec = getISOTZoneRec();
		myRec.set( origRec );
	}
}
