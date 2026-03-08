// Description: Java 25 edit object instance implementation for CFInt Tenant.

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

public class CFIntTenantEditObj
	implements ICFIntTenantEditObj
{
	protected ICFSecTenantObj orig;
	protected ICFSecTenant rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecClusterObj requiredContainerCluster;
	protected List<ICFSecTSecGroupObj> optionalComponentsTSecGroup;
	protected List<ICFIntTldObj> optionalComponentsTld;

	public CFIntTenantEditObj( ICFSecTenantObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecTenant origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerCluster = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecTenant rec = getRec();
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
			ICFSecTenant rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getTenantTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "Tenant" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecClusterObj scope = getRequiredContainerCluster();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredTenantName();
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
		if( subObj == null ) {
			subObj = ((ICFIntSchemaObj)getSchema()).getTSecGroupTableObj().readTSecGroupByUNameIdx( getRequiredId(),
				nextName, false );
		}
		if( subObj == null ) {
			subObj = ((ICFIntSchemaObj)getSchema()).getTldTableObj().readTldByNameIdx( nextName, false );
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
	public ICFSecTenantObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecTenantObj retobj = getSchema().getTenantTableObj().realiseTenant( (ICFIntTenantObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsTenant().forget();
	}

	@Override
	public ICFSecTenantObj read() {
		ICFSecTenantObj retval = getOrigAsTenant().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTenantObj read( boolean forceRead ) {
		ICFSecTenantObj retval = getOrigAsTenant().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTenantObj create() {
		copyRecToOrig();
		ICFSecTenantObj retobj = ((ICFIntSchemaObj)getOrigAsTenant().getSchema()).getTenantTableObj().createTenant( getOrigAsTenant() );
		if( retobj == getOrigAsTenant() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecTenantEditObj update() {
		getSchema().getTenantTableObj().updateTenant( (ICFSecTenantObj)this );
		return( null );
	}

	@Override
	public CFSecTenantEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getTenantTableObj().deleteTenant( getOrigAsTenant() );
		return( null );
	}

	@Override
	public ICFSecTenantTableObj getTenantTable() {
		return( orig.getSchema().getTenantTableObj() );
	}

	@Override
	public ICFSecTenantEditObj getEdit() {
		return( (ICFSecTenantEditObj)this );
	}

	@Override
	public ICFSecTenantEditObj getEditAsTenant() {
		return( (ICFSecTenantEditObj)this );
	}

	@Override
	public ICFSecTenantEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecTenantObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecTenantObj getOrigAsTenant() {
		return( (ICFSecTenantObj)orig );
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
	public ICFSecTenant getRec() {
		if( rec == null ) {
			rec = getOrigAsTenant().getSchema().getCFSecBackingStore().getFactoryTenant().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecTenant value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerCluster = null;
		}
	}

	@Override
	public ICFSecTenant getTenantRec() {
		return( (ICFSecTenant)getRec() );
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
	public CFLibDbKeyHash256 getRequiredId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredId(CFLibDbKeyHash256 id) {
		if (getPKey() != id) {
			setPKey(id);
			requiredContainerCluster = null;
			optionalComponentsTSecGroup = null;
			optionalComponentsTld = null;
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getTenantRec().getRequiredClusterId() );
	}

	@Override
	public String getRequiredTenantName() {
		return( getTenantRec().getRequiredTenantName() );
	}

	@Override
	public void setRequiredTenantName( String value ) {
		if( getTenantRec().getRequiredTenantName() != value ) {
			getTenantRec().setRequiredTenantName( value );
		}
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster() {
		return( getRequiredContainerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster( boolean forceRead ) {
		if( forceRead || ( requiredContainerCluster == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecClusterObj obj = ((ICFIntSchemaObj)getOrigAsTenant().getSchema()).getClusterTableObj().readClusterByIdIdx( getTenantRec().getRequiredClusterId() );
				requiredContainerCluster = obj;
				if( obj != null ) {
					requiredContainerCluster = obj;
				}
			}
		}
		return( requiredContainerCluster );
	}

	@Override
	public void setRequiredContainerCluster( ICFSecClusterObj value ) {
		if( rec == null ) {
			getTenantRec();
		}
		if( value != null ) {
			requiredContainerCluster = value;
			getTenantRec().setRequiredContainerCluster(value.getClusterRec());
		}
		requiredContainerCluster = value;
	}

	@Override
	public List<ICFSecTSecGroupObj> getOptionalComponentsTSecGroup() {
		List<ICFSecTSecGroupObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGroupTableObj().readTSecGroupByTenantIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGroupObj> getOptionalComponentsTSecGroup( boolean forceRead ) {
		List<ICFSecTSecGroupObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGroupTableObj().readTSecGroupByTenantIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFIntTldObj> getOptionalComponentsTld() {
		List<ICFIntTldObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTldTableObj().readTldByTenantIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFIntTldObj> getOptionalComponentsTld( boolean forceRead ) {
		List<ICFIntTldObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTldTableObj().readTldByTenantIdx( getPKey(),
			forceRead );
		return( retval );
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
		ICFSecTenant origRec = getOrigAsTenant().getTenantRec();
		ICFSecTenant myRec = getTenantRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecTenant origRec = getOrigAsTenant().getTenantRec();
		ICFSecTenant myRec = getTenantRec();
		myRec.set( origRec );
	}
}
