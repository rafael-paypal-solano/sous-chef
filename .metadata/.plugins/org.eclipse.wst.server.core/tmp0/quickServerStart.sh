#!/bin/sh
# /home/rsolano/Documents/workspaces/showcase/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/quickServerStart.sh
# Generated: Sun Mar 19 12:04:32 AST 2017

# Bootstrap values ...
binDir=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin
. $binDir/setupCmdLine.sh
cd /opt/IBM/WebSphere/AppServer/profiles/AppSrv01

# For debugging the server process:
# export DEBUG="-Djava.compiler=NONE -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=7777"

# Environment Settings
PLATFORM=`/bin/uname`
case $PLATFORM in
  AIX)
    EXTSHM=ON
    D_ARGS=""$D_ARGS" $DELIM -Dibm.websphere.preload.classes=true"
    LIBPATH="$WAS_LIBPATH":$LIBPATH
    export LIBPATH EXTSHM ;;
  Linux)
    LD_LIBRARY_PATH="$WAS_LIBPATH":$LD_LIBRARY_PATH
    D_ARGS=""$D_ARGS" $DELIM -Dibm.websphere.preload.classes=true"
    export LD_LIBRARY_PATH ;;
  SunOS)
    LD_LIBRARY_PATH="$WAS_LIBPATH":$LD_LIBRARY_PATH
    D_ARGS=""$D_ARGS" $DELIM -Dibm.websphere.preload.classes=true"
    export LD_LIBRARY_PATH ;;
  HP-UX)
    SHLIB_PATH="$WAS_LIBPATH":$SHLIB_PATH
    D_ARGS=""$D_ARGS" $DELIM -Dibm.websphere.preload.classes=true"
    export SHLIB_PATH ;;
  OS/390)
    PATH="$PATH":$binDir
    export PATH
    D_ARGS=""$D_ARGS" $DELIM -Dfile.encoding=ISO8859-1 $DELIM -Djava.ext.dirs="$JAVA_EXT_DIRS""
    D_ARGS=""$D_ARGS" $DELIM -Dwas.serverstart.cell="$WAS_CELL""
    D_ARGS=""$D_ARGS" $DELIM -Dwas.serverstart.node="$WAS_NODE""
    D_ARGS=""$D_ARGS" $DELIM -Dwas.serverstart.server="$1""
    X_ARGS="-Xnoargsconversion" ;;
esac



# Launch Command
exec "/opt/IBM/WebSphere/AppServer/java/bin/java"  $DEBUG "-Declipse.security" "-Dosgi.install.area=/opt/IBM/WebSphere/AppServer" "-Dosgi.configuration.area=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/servers/server1/configuration" "-Djava.awt.headless=true" "-Dosgi.framework.extensions=com.ibm.cds,com.ibm.ws.eclipse.adaptors" "-Xshareclasses:name=webspherev85_1.8_64_bundled_%g,nonFatal" "-Dcom.ibm.xtq.processor.overrideSecureProcessing=true" "-Xcheck:dump" "-Djava.security.properties=/opt/IBM/WebSphere/AppServer/properties/java.security" "-Dwas.debug.mode=true" "-Dcom.ibm.ws.classloader.j9enabled=true" "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=7777" "-Xbootclasspath/p:/opt/IBM/WebSphere/AppServer/lib/dertrjrt.jar:/opt/IBM/WebSphere/AppServer/java/jre/lib/ibmorb.jar" "-classpath" "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/properties:/opt/IBM/WebSphere/AppServer/properties:/opt/IBM/WebSphere/AppServer/lib/startup.jar:/opt/IBM/WebSphere/AppServer/lib/bootstrap.jar:/opt/IBM/WebSphere/AppServer/lib/jsf-nls.jar:/opt/IBM/WebSphere/AppServer/lib/lmproxy.jar:/opt/IBM/WebSphere/AppServer/lib/urlprotocols.jar:/opt/IBM/WebSphere/AppServer/deploytool/itp/batchboot.jar:/opt/IBM/WebSphere/AppServer/deploytool/itp/batch2.jar:/opt/IBM/WebSphere/AppServer/java/lib/tools.jar" "-Dibm.websphere.internalClassAccessMode=allow" "-Xms50m" "-Xmx512m" "-Xcompressedrefs" "-Xscmaxaot4M" "-Xscmx60M" "-Xquickstart" "-Dws.ext.dirs=/opt/IBM/WebSphere/AppServer/java/lib:/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/classes:/opt/IBM/WebSphere/AppServer/classes:/opt/IBM/WebSphere/AppServer/lib:/opt/IBM/WebSphere/AppServer/installedChannels:/opt/IBM/WebSphere/AppServer/lib/ext:/opt/IBM/WebSphere/AppServer/web/help:/opt/IBM/WebSphere/AppServer/deploytool/itp/plugins/com.ibm.etools.ejbdeploy/runtime" "-Dderby.system.home=/opt/IBM/WebSphere/AppServer/derby" "-Dcom.ibm.itp.location=/opt/IBM/WebSphere/AppServer/bin" "-Djava.util.logging.configureByServer=true" "-Duser.install.root=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01" "-Djava.ext.dirs=/opt/IBM/WebSphere/AppServer/tivoli/tam:/opt/IBM/WebSphere/AppServer/java/jre/lib/ext" "-Djavax.management.builder.initial=com.ibm.ws.management.PlatformMBeanServerBuilder" "-Dpython.cachedir=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/temp/cachedir" "-Dwas.install.root=/opt/IBM/WebSphere/AppServer" "-Djava.util.logging.manager=com.ibm.ws.bootstrap.WsLogManager" "-Dserver.root=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01" "-Dcom.ibm.security.jgss.debug=off" "-Dcom.ibm.security.krb5.Krb5Debug=off" "-Dcom.ibm.ws.management.event.pull_notification_timeout=120000" "-Dcom.ibm.websphere.persistence.ApplicationsExcludedFromJpaProcessing=*" "-Dcom.ibm.xml.xlxp.jaxb.opti.level=3" "-Djava.library.path=/opt/IBM/WebSphere/AppServer/lib/native/linux/x86_64/:/opt/IBM/WebSphere/AppServer/java/jre/lib/amd64/compressedrefs:/opt/IBM/WebSphere/AppServer/java/jre/lib/amd64:/opt/IBM/WebSphere/AppServer/bin:/u01/app/oracle/product/12.1.0/db_1/lib:/lib:/usr/lib:/usr/lib64:/usr/lib:" "-Djava.endorsed.dirs=/opt/IBM/WebSphere/AppServer/endorsed_apis:/opt/IBM/WebSphere/AppServer/java/jre/lib/endorsed" "-Djava.security.auth.login.config=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/properties/wsjaas.conf" "-Djava.security.policy=/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/properties/server.policy" "com.ibm.wsspi.bootstrap.WSPreLauncher" "-nosplash" "-application" "com.ibm.ws.bootstrap.WSLauncher" "com.ibm.ws.runtime.WsServer" "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/config" "HP-ENVY-15-x360-PCNode01Cell" "HP-ENVY-15-x360-PCNode01" "server1" &

