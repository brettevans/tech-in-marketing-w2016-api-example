#!/bin/bash -eu

# Provision Vagrant Machine

set -o pipefail

if ${trace:-false}; then
  set -x
fi

provision(){
  update-packages
}

update-packages(){
  echo "Updating Packages ..."
  sudo apt-get -y update
}

provision
