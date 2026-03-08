// Description: Java 25 edit object instance implementation for CFInt TSecGrpInc.

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

public class CFIntTSecGrpIncEditObj
	implements ICFIntTSecGrpIncEditObj
{
	protected ICFSecTSecGrpIncObj orig;
	protected ICFSecTSecGrpInc rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFSecTSecGroupObj requiredContainerGroup;
	protected ICFSecTSecGroupObj requiredParentSubGroup;

	public CFIntTSecGrpIncEditObj( ICFSecTSecGrpIncObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecTSecGrpInc origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerTenant = null;
		requiredContainerGroup = null;
		requiredParentSubGroup = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecTSecGrpInc rec = getRec();
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
			ICFSecTSecGrpInc rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getTSecGrpIncTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "TSecGrpInc" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecTSecGroupObj scope = getRequiredContainerGroup();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredTSecGrpIncId();
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
	public ICFSecTSecGrpIncObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecTSecGrpIncObj retobj = getSchema().getTSecGrpIncTableObj().realiseTSecGrpInc( (ICFIntTSecGrpIncObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsTSecGrpInc().forget();
	}

	@Override
	public ICFSecTSecGrpIncObj read() {
		ICFSecTSecGrpIncObj retval = getOrigAsTSecGrpInc().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTSecGrpIncObj read( boolean forceRead ) {
		ICFSecTSecGrpIncObj retval = getOrigAsTSecGrpInc().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTSecGrpIncObj create() {
		copyRecToOrig();
		ICFSecTSecGrpIncObj retobj = ((ICFIntSchemaObj)getOrigAsTSecGrpInc().getSchema()).getTSecGrpIncTableObj().createTSecGrpInc( getOrigAsTSecGrpInc() );
		if( retobj == getOrigAsTSecGrpInc() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecTSecGrpIncEditObj update() {
		getSchema().getTSecGrpIncTableObj().updateTSecGrpInc( (ICFSecTSecGrpIncObj)this );
		return( null );
	}

	@Override
	public CFSecTSecGrpIncEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getTSecGrpIncTableObj().deleteTSecGrpInc( getOrigAsTSecGrpInc() );
		return( null );
	}

	@Override
	public ICFSecTSecGrpIncTableObj getTSecGrpIncTable() {
		return( orig.getSchema().getTSecGrpIncTableObj() );
	}

	@Override
	public ICFSecTSecGrpIncEditObj getEdit() {
		return( (ICFSecTSecGrpIncEditObj)this );
	}

	@Override
	public ICFSecTSecGrpIncEditObj getEditAsTSecGrpInc() {
		return( (ICFSecTSecGrpIncEditObj)this );
	}

	@Override
	public ICFSecTSecGrpIncEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecTSecGrpIncObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecTSecGrpIncObj getOrigAsTSecGrpInc() {
		return( (ICFSecTSecGrpIncObj)orig );
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
	public ICFSecTSecGrpInc getRec() {
		if( rec == null ) {
			rec = getOrigAsTSecGrpInc().getSchema().getCFSecBackingStore().getFactoryTSecGrpInc().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecTSecGrpInc value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerTenant = null;
			requiredContainerGroup = null;
			requiredParentSubGroup = null;
		}
	}

	@Override
	public ICFSecTSecGrpInc getTSecGrpIncRec() {
		return( (ICFSecTSecGrpInc)getRec() );
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
	public CFLibDbKeyHash256 getRequiredTSecGrpIncId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredTSecGrpIncId(CFLibDbKeyHash256 tSecGrpIncId) {
		if (getPKey() != tSecGrpIncId) {
			setPKey(tSecGrpIncId);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTSecGrpIncRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTSecGroupId() {
		return( getTSecGrpIncRec().getRequiredTSecGroupId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredIncludeGroupId() {
		return( getTSecGrpIncRec().getRequiredIncludeGroupId() );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant() {
		return( getRequiredOwnerTenant( false ) );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant( boolean forceRead ) {
		if( forceRead || ( requiredOwnerTenant == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsTSecGrpInc().getSchema()).getTenantTableObj().readTenantByIdIdx( getTSecGrpIncRec().getRequiredTenantId() );
				requiredOwnerTenant = obj;
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public void setRequiredOwnerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getTSecGrpIncRec();
		}
		if( value != null ) {
			requiredOwnerTenant = value;
			getTSecGrpIncRec().setRequiredOwnerTenant(value.getTenantRec());
		}
		requiredOwnerTenant = value;
	}

	@Override
	public ICFSecTSecGroupObj getRequiredContainerGroup() {
		return( getRequiredContainerGroup( false ) );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredContainerGroup( boolean forceRead ) {
		if( forceRead || ( requiredContainerGroup == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTSecGroupObj obj = ((ICFIntSchemaObj)getOrigAsTSecGrpInc().getSchema()).getTSecGroupTableObj().readTSecGroupByIdIdx( getTSecGrpIncRec().getRequiredTSecGroupId() );
				requiredContainerGroup = obj;
				if( obj != null ) {
					requiredContainerGroup = obj;
				}
			}
		}
		return( requiredContainerGroup );
	}

	@Override
	public void setRequiredContainerGroup( ICFSecTSecGroupObj value ) {
		if( rec == null ) {
			getTSecGrpIncRec();
		}
		if( value != null ) {
			requiredContainerGroup = value;
			getTSecGrpIncRec().setRequiredContainerGroup(value.getTSecGroupRec());
		}
		requiredContainerGroup = value;
	}

	@Override
	public ICFSecTSecGroupObj getRequiredParentSubGroup() {
		return( getRequiredParentSubGroup( false ) );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredParentSubGroup( boolean forceRead ) {
		if( forceRead || ( requiredParentSubGroup == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTSecGroupObj obj = ((ICFIntSchemaObj)getOrigAsTSecGrpInc().getSchema()).getTSecGroupTableObj().readTSecGroupByIdIdx( getTSecGrpIncRec().getRequiredIncludeGroupId() );
				requiredParentSubGroup = obj;
			}
		}
		return( requiredParentSubGroup );
	}

	@Override
	public void setRequiredParentSubGroup( ICFSecTSecGroupObj value ) {
		if( rec == null ) {
			getTSecGrpIncRec();
		}
		if( value != null ) {
			requiredParentSubGroup = value;
			getTSecGrpIncRec().setRequiredParentSubGroup(value.getTSecGroupRec());
		}
		else {
			requiredParentSubGroup = null;
			getTSecGrpIncRec().setRequiredParentSubGroup((ICFSecTSecGroup)null);
		}
		requiredParentSubGroup = value;
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
		ICFSecTSecGrpInc origRec = getOrigAsTSecGrpInc().getTSecGrpIncRec();
		ICFSecTSecGrpInc myRec = getTSecGrpIncRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecTSecGrpInc origRec = getOrigAsTSecGrpInc().getTSecGrpIncRec();
		ICFSecTSecGrpInc myRec = getTSecGrpIncRec();
		myRec.set( origRec );
	}
}
