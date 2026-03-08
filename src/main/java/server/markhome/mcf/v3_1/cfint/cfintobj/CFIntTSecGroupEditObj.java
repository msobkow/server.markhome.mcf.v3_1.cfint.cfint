// Description: Java 25 edit object instance implementation for CFInt TSecGroup.

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

public class CFIntTSecGroupEditObj
	implements ICFIntTSecGroupEditObj
{
	protected ICFSecTSecGroupObj orig;
	protected ICFSecTSecGroup rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecTenantObj requiredContainerTenant;
	protected List<ICFSecTSecGrpIncObj> optionalComponentsInclude;
	protected List<ICFSecTSecGrpMembObj> optionalComponentsMember;
	protected List<ICFSecTSecGrpIncObj> requiredChildrenIncByGroup;

	public CFIntTSecGroupEditObj( ICFSecTSecGroupObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecTSecGroup origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerTenant = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecTSecGroup rec = getRec();
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
			ICFSecTSecGroup rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getTSecGroupTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "TSecGroup" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecTenantObj scope = getRequiredContainerTenant();
		return( scope );
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
	public ICFSecTSecGroupObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecTSecGroupObj retobj = getSchema().getTSecGroupTableObj().realiseTSecGroup( (ICFIntTSecGroupObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsTSecGroup().forget();
	}

	@Override
	public ICFSecTSecGroupObj read() {
		ICFSecTSecGroupObj retval = getOrigAsTSecGroup().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTSecGroupObj read( boolean forceRead ) {
		ICFSecTSecGroupObj retval = getOrigAsTSecGroup().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTSecGroupObj create() {
		copyRecToOrig();
		ICFSecTSecGroupObj retobj = ((ICFIntSchemaObj)getOrigAsTSecGroup().getSchema()).getTSecGroupTableObj().createTSecGroup( getOrigAsTSecGroup() );
		if( retobj == getOrigAsTSecGroup() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecTSecGroupEditObj update() {
		getSchema().getTSecGroupTableObj().updateTSecGroup( (ICFSecTSecGroupObj)this );
		return( null );
	}

	@Override
	public CFSecTSecGroupEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getTSecGroupTableObj().deleteTSecGroup( getOrigAsTSecGroup() );
		return( null );
	}

	@Override
	public ICFSecTSecGroupTableObj getTSecGroupTable() {
		return( orig.getSchema().getTSecGroupTableObj() );
	}

	@Override
	public ICFSecTSecGroupEditObj getEdit() {
		return( (ICFSecTSecGroupEditObj)this );
	}

	@Override
	public ICFSecTSecGroupEditObj getEditAsTSecGroup() {
		return( (ICFSecTSecGroupEditObj)this );
	}

	@Override
	public ICFSecTSecGroupEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecTSecGroupObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecTSecGroupObj getOrigAsTSecGroup() {
		return( (ICFSecTSecGroupObj)orig );
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
	public ICFSecTSecGroup getRec() {
		if( rec == null ) {
			rec = getOrigAsTSecGroup().getSchema().getCFSecBackingStore().getFactoryTSecGroup().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecTSecGroup value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerTenant = null;
		}
	}

	@Override
	public ICFSecTSecGroup getTSecGroupRec() {
		return( (ICFSecTSecGroup)getRec() );
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
	public CFLibDbKeyHash256 getRequiredTSecGroupId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredTSecGroupId(CFLibDbKeyHash256 tSecGroupId) {
		if (getPKey() != tSecGroupId) {
			setPKey(tSecGroupId);
			requiredContainerTenant = null;
			optionalComponentsInclude = null;
			optionalComponentsMember = null;
			requiredChildrenIncByGroup = null;
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTSecGroupRec().getRequiredTenantId() );
	}

	@Override
	public String getRequiredName() {
		return( getTSecGroupRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getTSecGroupRec().getRequiredName() != value ) {
			getTSecGroupRec().setRequiredName( value );
		}
	}

	@Override
	public boolean getRequiredIsVisible() {
		return( getTSecGroupRec().getRequiredIsVisible() );
	}

	@Override
	public void setRequiredIsVisible( boolean value ) {
		if( getTSecGroupRec().getRequiredIsVisible() != value ) {
			getTSecGroupRec().setRequiredIsVisible( value );
		}
	}

	@Override
	public ICFSecTenantObj getRequiredContainerTenant() {
		return( getRequiredContainerTenant( false ) );
	}

	@Override
	public ICFSecTenantObj getRequiredContainerTenant( boolean forceRead ) {
		if( forceRead || ( requiredContainerTenant == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsTSecGroup().getSchema()).getTenantTableObj().readTenantByIdIdx( getTSecGroupRec().getRequiredTenantId() );
				requiredContainerTenant = obj;
				if( obj != null ) {
					requiredContainerTenant = obj;
				}
			}
		}
		return( requiredContainerTenant );
	}

	@Override
	public void setRequiredContainerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getTSecGroupRec();
		}
		if( value != null ) {
			requiredContainerTenant = value;
			getTSecGroupRec().setRequiredContainerTenant(value.getTenantRec());
		}
		requiredContainerTenant = value;
	}

	@Override
	public List<ICFSecTSecGrpIncObj> getOptionalComponentsInclude() {
		List<ICFSecTSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().readTSecGrpIncByGroupIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> getOptionalComponentsInclude( boolean forceRead ) {
		List<ICFSecTSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().readTSecGrpIncByGroupIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> getOptionalComponentsMember() {
		List<ICFSecTSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpMembTableObj().readTSecGrpMembByGroupIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> getOptionalComponentsMember( boolean forceRead ) {
		List<ICFSecTSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpMembTableObj().readTSecGrpMembByGroupIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> getRequiredChildrenIncByGroup() {
		List<ICFSecTSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().readTSecGrpIncByIncludeIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpIncObj> getRequiredChildrenIncByGroup( boolean forceRead ) {
		List<ICFSecTSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().readTSecGrpIncByIncludeIdx( getPKey(),
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
		ICFSecTSecGroup origRec = getOrigAsTSecGroup().getTSecGroupRec();
		ICFSecTSecGroup myRec = getTSecGroupRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecTSecGroup origRec = getOrigAsTSecGroup().getTSecGroupRec();
		ICFSecTSecGroup myRec = getTSecGroupRec();
		myRec.set( origRec );
	}
}
