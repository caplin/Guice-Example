#!/bin/sh
#
# Start the ApamaRatesAdapter Java DataSource.
#
# $1 - Path to java executable
# $2 - Path to datasource XML file
# $3 - Path to fields XML file
# $4 - Java definitions ( optional )
#
# Returns the process id of the Java process.
#

"$1" $4 -cp lib/ApamaRatesAdapter.jar com.caplin.apama.rates.ApamaRatesAdapter  -f $2 -e $3 > var/java.log 2>&1 &
echo $!
