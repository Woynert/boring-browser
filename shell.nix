with import <nixpkgs> {};

mkShell {
	name = "Android signing";
	buildInputs = [
		gradle
		openssl
		temurin-jre-bin-17 # keytool
		# apksigner (also available on the sdk) -> sdk/build-tools/36.0.0/zipalign
		# zipalign (from sdk, not on the nix store) -> sdk/build-tools/36.0.0/zipalign
	];
	shellHook = ''
		# git prompt
		source ${git}/share/git/contrib/completion/git-prompt.sh
		PS1='\[\033[0;33m\]nix:\w\[\033[0m\] $(__git_ps1 %s)\n$ '
	'';
}
