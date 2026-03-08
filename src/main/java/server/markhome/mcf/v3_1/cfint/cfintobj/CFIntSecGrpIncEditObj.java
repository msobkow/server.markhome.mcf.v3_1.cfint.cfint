// Description: Java 25 edit object instance implementation for CFInt SecGrpInc.

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

public class CFIntSecGrpIncEditObj
	implements ICFIntSecGrpIncEditObj
{
	protected ICFSecSecGrpIncObj orig;
	protected ICFSecSecGrpInc rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecClusterObj requiredOwnerCluster;
	protected ICFSecSecGroupObj requiredContainerGroup;
	protected ICFSecSecGroupObj requiredParentSubGroup;

	public CFIntSecGrpIncEditObj( ICFSecSecGrpIncObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSecGrpInc origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerCluster = null;
		requiredContainerGroup = null;
		requiredParentSubGroup = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecSecGrpInc rec = getRec();
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
			ICFSecSecGrpInc rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getSecGrpIncTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SecGrpInc" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecSecGroupObj scope = getRequiredContainerGroup();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredSecGrpIncId();
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
	public ICFSecSecGrpIncObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSecGrpIncObj retobj = getSchema().getSecGrpIncTableObj().realiseSecGrpInc( (ICFIntSecGrpIncObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSecGrpInc().forget();
	}

	@Override
	public ICFSecSecGrpIncObj read() {
		ICFSecSecGrpIncObj retval = getOrigAsSecGrpInc().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecGrpIncObj read( boolean forceRead ) {
		ICFSecSecGrpIncObj retval = getOrigAsSecGrpInc().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecGrpIncObj create() {
		copyRecToOrig();
		ICFSecSecGrpIncObj retobj = ((ICFIntSchemaObj)getOrigAsSecGrpInc().getSchema()).getSecGrpIncTableObj().createSecGrpInc( getOrigAsSecGrpInc() );
		if( retobj == getOrigAsSecGrpInc() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSecGrpIncEditObj update() {
		getSchema().getSecGrpIncTableObj().updateSecGrpInc( (ICFSecSecGrpIncObj)this );
		return( null );
	}

	@Override
	public CFSecSecGrpIncEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSecGrpIncTableObj().deleteSecGrpInc( getOrigAsSecGrpInc() );
		return( null );
	}

	@Override
	public ICFSecSecGrpIncTableObj getSecGrpIncTable() {
		return( orig.getSchema().getSecGrpIncTableObj() );
	}

	@Override
	public ICFSecSecGrpIncEditObj getEdit() {
		return( (ICFSecSecGrpIncEditObj)this );
	}

	@Override
	public ICFSecSecGrpIncEditObj getEditAsSecGrpInc() {
		return( (ICFSecSecGrpIncEditObj)this );
	}

	@Override
	public ICFSecSecGrpIncEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSecGrpIncObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSecGrpIncObj getOrigAsSecGrpInc() {
		return( (ICFSecSecGrpIncObj)orig );
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
	public ICFSecSecGrpInc getRec() {
		if( rec == null ) {
			rec = getOrigAsSecGrpInc().getSchema().getCFSecBackingStore().getFactorySecGrpInc().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecGrpInc value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerCluster = null;
			requiredContainerGroup = null;
			requiredParentSubGroup = null;
		}
	}

	@Override
	public ICFSecSecGrpInc getSecGrpIncRec() {
		return( (ICFSecSecGrpInc)getRec() );
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
	public CFLibDbKeyHash256 getRequiredSecGrpIncId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredSecGrpIncId(CFLibDbKeyHash256 secGrpIncId) {
		if (getPKey() != secGrpIncId) {
			setPKey(secGrpIncId);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getSecGrpIncRec().getRequiredClusterId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecGroupId() {
		return( getSecGrpIncRec().getRequiredSecGroupId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredIncludeGroupId() {
		return( getSecGrpIncRec().getRequiredIncludeGroupId() );
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster() {
		return( getRequiredOwnerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster( boolean forceRead ) {
		if( forceRead || ( requiredOwnerCluster == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecClusterObj obj = ((ICFIntSchemaObj)getOrigAsSecGrpInc().getSchema()).getClusterTableObj().readClusterByIdIdx( getSecGrpIncRec().getRequiredClusterId() );
				requiredOwnerCluster = obj;
			}
		}
		return( requiredOwnerCluster );
	}

	@Override
	public void setRequiredOwnerCluster( ICFSecClusterObj value ) {
		if( rec == null ) {
			getSecGrpIncRec();
		}
		if( value != null ) {
			requiredOwnerCluster = value;
			getSecGrpIncRec().setRequiredOwnerCluster(value.getClusterRec());
		}
		requiredOwnerCluster = value;
	}

	@Override
	public ICFSecSecGroupObj getRequiredContainerGroup() {
		return( getRequiredContainerGroup( false ) );
	}

	@Override
	public ICFSecSecGroupObj getRequiredContainerGroup( boolean forceRead ) {
		if( forceRead || ( requiredContainerGroup == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecSecGroupObj obj = ((ICFIntSchemaObj)getOrigAsSecGrpInc().getSchema()).getSecGroupTableObj().readSecGroupByIdIdx( getSecGrpIncRec().getRequiredSecGroupId() );
				requiredContainerGroup = obj;
				if( obj != null ) {
					requiredContainerGroup = obj;
				}
			}
		}
		return( requiredContainerGroup );
	}

	@Override
	public void setRequiredContainerGroup( ICFSecSecGroupObj value ) {
		if( rec == null ) {
			getSecGrpIncRec();
		}
		if( value != null ) {
			requiredContainerGroup = value;
			getSecGrpIncRec().setRequiredContainerGroup(value.getSecGroupRec());
		}
		requiredContainerGroup = value;
	}

	@Override
	public ICFSecSecGroupObj getRequiredParentSubGroup() {
		return( getRequiredParentSubGroup( false ) );
	}

	@Override
	public ICFSecSecGroupObj getRequiredParentSubGroup( boolean forceRead ) {
		if( forceRead || ( requiredParentSubGroup == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecSecGroupObj obj = ((ICFIntSchemaObj)getOrigAsSecGrpInc().getSchema()).getSecGroupTableObj().readSecGroupByIdIdx( getSecGrpIncRec().getRequiredIncludeGroupId() );
				requiredParentSubGroup = obj;
			}
		}
		return( requiredParentSubGroup );
	}

	@Override
	public void setRequiredParentSubGroup( ICFSecSecGroupObj value ) {
		if( rec == null ) {
			getSecGrpIncRec();
		}
		if( value != null ) {
			requiredParentSubGroup = value;
			getSecGrpIncRec().setRequiredParentSubGroup(value.getSecGroupRec());
		}
		else {
			requiredParentSubGroup = null;
			getSecGrpIncRec().setRequiredParentSubGroup((ICFSecSecGroup)null);
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
		ICFSecSecGrpInc origRec = getOrigAsSecGrpInc().getSecGrpIncRec();
		ICFSecSecGrpInc myRec = getSecGrpIncRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSecGrpInc origRec = getOrigAsSecGrpInc().getSecGrpIncRec();
		ICFSecSecGrpInc myRec = getSecGrpIncRec();
		myRec.set( origRec );
	}
}
