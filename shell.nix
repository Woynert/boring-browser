with import <nixpkgs> {};

mkShell {
	name = "Android signing";
	buildInputs = [
		openssl
		#temurin-jre-bin-17 # keytool; no JDK
		openjdk17-bootstrap # keytool; it's actually temurin JRE + JDK
		android-tools # adb
		# gradle -> use local ./gradlew script instead
		# apksigner (also available on the sdk) -> sdk/build-tools/36.0.0/apksigner
		# zipalign (from sdk, not on the nix store) -> sdk/build-tools/36.0.0/zipalign
	];
	shellHook = ''
		# git prompt
		source ${git}/share/git/contrib/completion/git-prompt.sh
		PS1='\[\033[0;33m\]nix:\w\[\033[0m\] $(__git_ps1 %s)\n$ '
	'';
}
